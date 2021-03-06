package cn.edu.bjtu.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.ClientSecurityService;
import cn.edu.bjtu.util.Constant;
import cn.edu.bjtu.util.Encrypt;
import cn.edu.bjtu.vo.Userinfo;

@Controller
public class ClientSecurityController {

	@Autowired
	ClientSecurityService clientSecurityService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/mysecurity")
	/**
	 * 获取我的安全设置页面
	 * @param session
	 * @return
	 */
	public ModelAndView getMySercurityPage(HttpSession session) {
		String userId = (String) session.getAttribute(Constant.USER_ID);
		Userinfo userinfo = clientSecurityService.getUserById(userId);
		mv.addObject("userinfo", userinfo);
		mv.setViewName("mgmt_a_security");
		return mv;

	}

	/**
	 * 获取修改密码表单
	 * @return
	 */
	@RequestMapping("getchangepasswordpage")
	public ModelAndView gotoChangePasswordPage() {

		mv.setViewName("mgmt_a_security2");
		return mv;
	}

	/*
	 * 修改密码
	 */
	@RequestMapping("changepassword")
	public ModelAndView changePassword(HttpSession session, String oldPassword,
			String newPassword, String repeatPassword) {
		String userId = (String) session.getAttribute(Constant.USER_ID);
		boolean flag = false;
		String psw_old = Encrypt.MD5(oldPassword);//add by RussWest0 at 2015年6月7日,上午11:21:01 
		flag = clientSecurityService.checkOldPassword(psw_old, userId);
		if (flag == true)// 旧密码正确 的情况
		{
			if (newPassword.equals(repeatPassword))// 两次新密码一样
			{
				//add by RussWest0 at 2015年6月7日,上午11:19:49 
//				密码加密
				String psw = Encrypt.MD5(newPassword);
				clientSecurityService.changePassword(psw, userId);// 修改密码
				String msg = "修改密码成功";
				mv.addObject("msg", msg);
				mv.setViewName("mgmt_a_security");
				return mv;
			} else// 两次新密码不一样
			{
				String msg = "两次密码输入不一致，请重新输入新密码!";
				mv.addObject("msg", msg);
				mv.setViewName("mgmt_a_security2");
				return mv;
			}

		} else// 旧密码错误
		{
			String msg = "原始密码错误!请重新输入";
			mv.addObject("msg", msg);
			mv.setViewName("mgmt_a_security2");
			return mv;
		}
	}

	/**
	 * 绑定邮箱页面
	 * @param session
	 * @return
	 */
	@RequestMapping("getbindemailpage")
	public ModelAndView gotoBindEmailPage() {
		mv.setViewName("mgmt_a_security4");
		return mv;
	}

	@RequestMapping("bindemail")
	public String bindEmail(HttpSession session, String email,
			HttpServletRequest request, HttpServletResponse response) {
		//String userId = (String) session.getAttribute(Constant.USER_ID);
		clientSecurityService.bindEmail(email, session);
		
		return "redirect:mysecurity";
		
	}

	/**			     
	 * 获取更改邮箱绑定页面
	 * @param session
	 * @return
	 */
	@RequestMapping("getchangebindemailpage")
	public ModelAndView gotoChangeEmailPage(HttpSession session) {
		String userId = (String) session.getAttribute(Constant.USER_ID);
		Userinfo userinfo = clientSecurityService.getUserById(userId);
		String email = userinfo.getEmail();
		mv.addObject("email", email);
		mv.setViewName("mgmt_a_security4b");
		return mv;
	}

	@RequestMapping("changebindemail")
	public String changeBindEmail(HttpSession session, String newEmail,
			HttpServletRequest request, HttpServletResponse response) {
		clientSecurityService.changeBindEmail(newEmail, session);
		return "redirect:mysecurity";

	}
	@RequestMapping("getsetquestionpage")
	public ModelAndView gotoSetSecurityQuestionPage()
	{
		mv.setViewName("mgmt_a_security5");//设置
		return mv;
	}
	
	@RequestMapping("getchangequestionpage")
	public ModelAndView gotoChangeSecurityQuestionPage(HttpSession session)
	{
		String userId=(String )session.getAttribute(Constant.USER_ID);
		Userinfo userinfo=clientSecurityService.getUserById(userId);
		
		mv.addObject("userinfo", userinfo);
		mv.setViewName("mgmt_a_security5a");//修改
		return mv;
	}
	@RequestMapping("setquestion")
	public ModelAndView setSecurityQuestion(String question1,String question2,String question3,
			String answer1,String answer2,String answer3,HttpSession session)
	{
		String userId=(String )session.getAttribute(Constant.USER_ID);
		boolean flag=clientSecurityService.setSecurityQuestion(question1,question2,question3,answer1,answer2,answer3,userId);
		if(flag== true)
		{
			String msg="修改密保问题成功!";
			Userinfo userinfo=clientSecurityService.getUserById(userId);
			mv.addObject("userinfo", userinfo);//更新页面上的bean
			mv.addObject("msg", msg);
			mv.setViewName("mgmt_a_security");
			return mv;
		}
		else
		{
			String msg="修改密保问题失败，请重新设置!";
			mv.addObject("msg", msg);
			mv.setViewName("mgmt_a_security5");
			return mv;
		}
	}
	@RequestMapping("checkanswer")
	public ModelAndView checkSecurityQuestion(String answer1,String answer2,String answer3,HttpSession session)
	{
		String userId=(String )session.getAttribute(Constant.USER_ID);
		boolean flag=clientSecurityService.checkAnswer(answer1,answer2,answer3,userId);
		if(flag==true)
		{
			String msg="答案正确！";
			mv.addObject("msg",msg);
			mv.setViewName("mgmt_a_security5b");//验证成功后，设置页面
			return mv;
		}
		else 
		{
			String msg="答案不正确，请重新输入！";
			mv.addObject("msg",msg);
			
			mv.setViewName("mgmt_a_security5a");//验证失败，返回验证页面
			return mv;
		}
	}
	
}

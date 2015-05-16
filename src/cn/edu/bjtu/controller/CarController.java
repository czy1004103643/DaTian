package cn.edu.bjtu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.bjtu.service.CarService;
import cn.edu.bjtu.service.CompanyService;
import cn.edu.bjtu.service.LinetransportService;
import cn.edu.bjtu.vo.Carinfo;
import cn.edu.bjtu.vo.Carrierinfo;
import cn.edu.bjtu.vo.Carteam;
import cn.edu.bjtu.vo.Driverinfo;
import cn.edu.bjtu.vo.Linetransport;

@Controller
public class CarController {

	@Resource(name = "carServiceImpl")
	CarService carService;
	@Resource
	CompanyService companyService;
	@Resource
	LinetransportService linetransportService;

	ModelAndView mv = new ModelAndView();

	@RequestMapping("/car")
	/**
	 * �������г�����Ϣ����ͼ��ѯ��
	 * @return
	 */
	public ModelAndView getAllCar(@RequestParam int flag,
			HttpServletRequest request) {
		int Display=10;//Ĭ�ϵ�ÿҳ��С
		int PageNow=1;//Ĭ�ϵĵ�ǰҳ��
		
		if (flag == 0) {
			List carList = carService.getAllCar(Display,PageNow);
			int count = carService.getTotalRows("All", "All", "All", "All");// ��ȡ�ܼ�¼��,����Ҫwhere�Ӿ䣬���Բ�������All
			System.out.println("count+"+count);
			int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
			mv.addObject("count", count);
			mv.addObject("pageNum", pageNum);
			mv.addObject("pageNow", PageNow);
			
			mv.addObject("carList", carList);
			mv.setViewName("resource_list3");
		} else if (flag == 1) {
			// ������sessionȡid
			 String carrierId=(String)request.getSession().getAttribute("userId");
			//String carrierId = "C-0002";// ɾ��
			List carList = carService.getCompanyCar(carrierId);
			mv.addObject("carList", carList);
			mv.setViewName("mgmt_r_car");// ��̨��ûʵ��
		}
		return mv;
	}

	@RequestMapping(value = "/cardetail", method = RequestMethod.GET)
	/**
	 * ��ȡ�ض��ĳ�����Ϣ
	 * ͬʱ���ع�˾�ͳ�������������Ϣ
	 * @param
	 * @return
	 */
	public ModelAndView getCarInfo(@RequestParam("carId") String carId,
			@RequestParam("carrierId") String carrierId,
			@RequestParam("linetransportId") String linetransportId,
			@RequestParam("flag") int flag, HttpServletRequest request) {
		Carinfo carInfo = carService.getCarInfo(carId);// ������Ϣ
		mv.addObject("carInfo", carInfo);

		Linetransport line = linetransportService
				.getLinetransportInfo(linetransportId);// ������Ϣ
		mv.addObject("linetransportInfo", line);
		if (flag == 0) {// ��Ӧ��Դ����������
			Carrierinfo carrierInfo = companyService.getCarrierInfo(carrierId);

			mv.addObject("carrierInfo", carrierInfo);

			mv.setViewName("resource_detail3");
		} else if (flag == 1)// ��Ӧ�ҵ���Ϣ�г�����Ϣ
		{
			// ��Ҫ˾����Ϣ
			Driverinfo driverinfo = carService.getDriverByCarId(carId);
			mv.addObject("driverInfo", driverinfo);
			mv.setViewName("mgmt_r_car4");
		} else if (flag == 2)// ��Ӧ�ҵ���Ϣ-����-����
		{
			// ��Ҫ˾����Ϣ
			Driverinfo driverinfo = carService.getDriverByCarId(carId);
			mv.addObject("driverInfo", driverinfo);
			// �˴�Ҫ�������˾����˾���������Թ�ѡ��
			// String carrierId=(String)request.getSession().getAttribute("carrierId");
			// carrierId = "C-0002";// ɾ��
			List driverNameList = carService.getAllDriverName(carrierId);
			mv.addObject("driverNameList", driverNameList);
			mv.setViewName("mgmt_r_car3");
		}
		return mv;
	}

	@RequestMapping("carselected")
	/**
	 * ���ط���ɸѡ�����ĳ�����Ϣ
	 * @param carLocation1
	 * @param endPlace
	 * @param carUse
	 * @param carColdStorage
	 * @param carLength
	 * @param carLocation
	 * @param Display
	 * @param PageNow
	 * @return
	 */
	public ModelAndView getSelectedCar(@RequestParam String carLocation,
			@RequestParam String endPlace, @RequestParam String carBase,
			@RequestParam String carLength,
			@RequestParam String carWeight,
			//@RequestParam String location,
			@RequestParam int Display, @RequestParam int PageNow,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("����ɸѡ�����Ŀ�����");
		
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("�Ѿ����������");

		List carList = carService.getSelectedCar(
				carLocation,
				//endPlace, û��Ŀ�ĳ���
				carBase, carLength, carWeight,
				//location, û�ж�λ��Ϣ
				Display, PageNow);
		int count = carService.getTotalRows(carLocation,
				//endPlace,
				carBase, carLength, carWeight
				//, location
				);// ��ȡ�ܼ�¼��

		int pageNum = (int) Math.ceil(count * 1.0 / Display);// ҳ��
		//System.out.println("�ܼ�¼��+"+count);
		//System.out.println("ҳ��+"+pageNum);
		mv.addObject("carList", carList);
		mv.addObject("count", count);
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageNow", PageNow);
		mv.setViewName("resource_list3");
		
		return mv;
	}

	@RequestMapping("driver")
	/**
	 * ��ȡ˾���б�
	 * @param flag
	 * @return
	 */
	public ModelAndView getAllDriver(@RequestParam int flag,
			HttpServletRequest request, HttpServletResponse response) {
		// ��session��ȡ��id��ѯ
		if (flag == 0) {//���е�˾����Ϣ
			List driverList = carService.getAllDriver();
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		} else if (flag == 1) {//��˾˾���б�
			// ������sessionȡid
			String carrierId=(String)request.getSession().getAttribute("userId");
			// String carrierId = "C-0002";// ɾ��
			List driverList = carService.getCompanyDriver(carrierId);
			mv.addObject("driverList", driverList);
			mv.setViewName("mgmt_r_driver");
		}

		return mv;
	}

	@RequestMapping("driverdetail")
	/**
	 * ˾����Ϣ����
	 * @param driverId
	 * @param flag
	 * @return
	 */
	public ModelAndView getDriverInfo(@RequestParam String driverId,
			@RequestParam int flag) {
		Driverinfo driver = carService.getDriverInfo(driverId);
		mv.addObject("driver", driver);
		if (flag == 1) {// ��Ӧ˾������
			mv.setViewName("mgmt_r_driver4");
		} else if (flag == 2)// ��Ӧ˾������
		{
			mv.setViewName("mgmt_r_driver3");
		}

		return mv;
	}

	@RequestMapping(value = "insertCar", method = RequestMethod.POST)
	/**
	 * ����������Ϣ
	 * @param carNum
	 * @param carTeam
	 * @param locationType
	 * @param carType
	 * @param carBase
	 * @param carBrand
	 * @param carUse
	 * @param carLength
	 * @param carWidth
	 * @param carHeight
	 * @param carWeight
	 * @param driverName
	 * @param purchaseTime
	 * @param storage
	 * @param startPlace
	 * @param endPlace
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertCar(@RequestParam String carNum,
			@RequestParam String carTeam, @RequestParam String locationType,
			@RequestParam String carType, @RequestParam String carBase,
			@RequestParam String carBrand, @RequestParam String carUse,
			@RequestParam double carLength, @RequestParam double carWidth,
			@RequestParam double carHeight, @RequestParam double carWeight,
			@RequestParam String driverName, @RequestParam String purchaseTime,
			@RequestParam String storage, @RequestParam String startPlace,
			@RequestParam String endPlace, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("���������");
		String carrierId=(String)request.getSession().getAttribute("userId");
		//String carrierId = "C-0002";// ɾ��
		/*
		 * boolean flag = linetransportService.insertLine(lineName, startPlace,
		 * endPlace, onWayTime, type, refPrice, remarks,carrierId);
		 */
		boolean flag = carService.insertCar(carNum, carTeam, locationType,
				carBase, carBrand, carType, carUse, carLength, carWidth,
				carHeight, carWeight, driverName, purchaseTime, storage,
				startPlace, endPlace, carrierId);
		System.out.println("flag+" + flag);
		if (flag == true) {
			try {
				System.out.println("redirect֮ǰ");
				response.sendRedirect("car?flag=1");// �ض�����ʾ���µĽ�� error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
				System.out.println("redirect֮��");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("car������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}

	@RequestMapping(value="/insertDriver",method = RequestMethod.POST)
	/**
	 * ����˾��
	 * @param name
	 * @param sex
	 * @param licenceRate
	 * @param phone
	 * @param IDCard
	 * @param licenceNum
	 * @param licenceTime
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView insertDriver(@RequestParam String name,
			@RequestParam String sex, @RequestParam String licenceRate,
			@RequestParam String phone, @RequestParam String IDCard,
			@RequestParam String licenceNum, @RequestParam String licenceTime,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("����driver������insert");
		String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��

		boolean flag = carService.insertDriver(name, sex, licenceRate, phone,
				IDCard, licenceNum, licenceTime, carrierId);
		System.out.println("flag+" + flag);
		if (flag == true) {
			try {
				System.out.println("redirect֮ǰ");
				response.sendRedirect("driver?flag=1");// �ض�����ʾ���µĽ��
														// error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
				System.out.println("redirect֮��");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("driver������ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}
	
	@RequestMapping(value = "updateCar", method = RequestMethod.POST)
	/**
	 * * ���³�����Ϣ��������˾����·�ߣ�
	 * @param id
	 * @param carNum
	 * @param carTeam
	 * @param locType
	 * @param GPSText
	 * @param carType
	 * @param carBase
	 * @param carBrand
	 * @param carUse
	 * @param carLength
	 * @param carWidth
	 * @param carHeight
	 * @param carWeight
	 * @param carPurTime
	 * @param storage
	 * @param driverName
	 * @param startPlace
	 * @param endPlace
	 * @param stopPlace
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateCar(
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String carNum, @RequestParam String carTeam,
			@RequestParam String locType, 
			@RequestParam String GPSText,// ȱ�ٲ���
			@RequestParam String carType,
			@RequestParam String carBase,
			@RequestParam String carBrand,
			@RequestParam String carUse,
			@RequestParam double carLength,
			@RequestParam double carWidth,
			@RequestParam double carHeight,
			@RequestParam double carWeight,
			@RequestParam String carPurTime,
			@RequestParam String storage,
			@RequestParam String driverName,
			@RequestParam String startPlace,
			@RequestParam String endPlace,
			@RequestParam String stopPlace,
			HttpServletRequest request,
			HttpServletResponse response) {

		// �˴���ȡsession���carrierid�����淽������һ������
		String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		System.out.println("in controller");//null
		
		boolean flag = carService.updateCar(id, carNum, carTeam, locType, 
				 GPSText, carType, carBase, carBrand, carUse, carLength, carWidth,
				 carHeight,carWeight, carPurTime, storage, driverName, startPlace,
				 endPlace, stopPlace, carrierId);
		
		System.out.println("out updateCar");//null
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("car?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("car���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}
	
	@RequestMapping(value="/updateDriver",method = RequestMethod.POST)
	/**
	 * * ����˾����Ϣ
	 * @param id
	 * @param name
	 * @param sex
	 * @param IDCard
	 * @param licenceNum
	 * @param licenceRate
	 * @param licenceTime
	 * @param phone
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateDriver(
			@RequestParam String id,// GET��ʽ���룬��action��
			@RequestParam String name,
			@RequestParam String sex, 
			@RequestParam String IDCard,
			@RequestParam String licenceNum,
			@RequestParam String licenceRate,
			 @RequestParam String licenceTime, @RequestParam String phone, 
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("����driver������update");
		String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��

		boolean flag = carService.updateDriver(id, name, sex, IDCard, licenceNum, 
				licenceRate, licenceTime, phone, carrierId);
		System.out.println("flag+" + flag);
		if (flag == true) {
			try {
				System.out.println("redirect֮ǰ");
				response.sendRedirect("driver?flag=1");// �ض�����ʾ���µĽ��
														// error,�޷��ض���
				// mv.setViewName("mgmt_r_car");
				System.out.println("redirect֮��");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("driver���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}
	
	@RequestMapping(value = "cardelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteCar(
			@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("����ɾ��������");
		System.out.println(id);
		// �˴���ȡsession���carrierid�����淽������һ������
		//String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		boolean flag = carService.deleteCar(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("car?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("ɾ�����ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}
	@RequestMapping(value = "driverdelete", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteDriver(
			@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("����ɾ��������");
		System.out.println(id);
		// �˴���ȡsession���carrierid�����淽������һ������
		//String carrierId=(String)request.getSession().getAttribute("userId");
		// String carrierId = "C-0002";// ɾ��
		boolean flag = carService.deleteDriver(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("driver?flag=1");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("ɾ�����ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}
	
	@RequestMapping("carteam")
	/**
	 * ��ȡ�����б�
	 * @return
	 */
	public ModelAndView getCarteam(HttpServletRequest request, HttpServletResponse response) {
		// ��session��ȡ��id��ѯ
			// ������sessionȡid
			String carrierId=(String)request.getSession().getAttribute("userId");
			// String carrierId = "C-0002";// ɾ��
			List carteamList = carService.getCarteam(carrierId);
			mv.addObject("carteamList", carteamList);
			mv.setViewName("mgmt_r_car_fleet");

		return mv;
	}
	
	@RequestMapping(value = "/carteamdetail", method = RequestMethod.GET)
	/**
	 * ��ȡ�ض��ĳ�����Ϣ
	 * @param
	 * @return
	 */
	public ModelAndView getCarteamDetail(@RequestParam String id,
			@RequestParam("flag") int flag, HttpServletRequest request) {
		Carteam carteaminfo = carService.getCarteamInfo(id);// ������Ϣ
		mv.addObject("carteaminfo", carteaminfo);
		System.out.println("����getCarteamDetail������ "+id);
		if (flag == 1)// ��Ӧ������Ϣ�鿴
		{
			mv.setViewName("mgmt_r_car_fleet4");
		} else if (flag == 2)// ��Ӧ������Ϣ����
		{
			mv.setViewName("mgmt_r_car_fleet3");
		}
		return mv;
	}
	
	@RequestMapping(value = "insertcarteam", method = RequestMethod.POST)
	/**
	 */
	public ModelAndView insertCarteam(@RequestParam String teamName, @RequestParam String carCount,
			@RequestParam String chief, @RequestParam String phone,
			@RequestParam String explaination, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("���������");
		String carrierId=(String)request.getSession().getAttribute("userId");
		boolean flag = carService.insertCarteam(teamName, carCount, chief, phone,
				explaination, carrierId);
		//boolean flag=true;
		System.out.println("flag+" + flag);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("carteam");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("���Ӻ��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}
	
	@RequestMapping(value = "deletecarteam", method = RequestMethod.GET)
	/**
	 * ɾ��
	 */
	public ModelAndView deleteCarteam(
			@RequestParam String id,// GET��ʽ���룬��action��
			HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("����ɾ��������");
		
		boolean flag = carService.deleteCarteam(id);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("carteam");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("ɾ�����ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;

	}
	
	@RequestMapping(value = "updatecarteam", method = RequestMethod.POST)
	/**
	 */
	public ModelAndView updateCarteam(@RequestParam String id,
			@RequestParam String teamName, @RequestParam String carCount,
			@RequestParam String chief, @RequestParam String phone,
			@RequestParam String explaination, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("���������");
		boolean flag = carService.updateCarteam(id,teamName, carCount, chief, phone,
				explaination);
		//boolean flag=true;
		System.out.println("flag+" + flag);
		if (flag == true) {
			// mv.setViewName("mgmt_r_line");
			try {
				response.sendRedirect("carteam");// �ض�����ʾ���µĽ��
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// �˴�Ӧ�ü�¼��־
				System.out.println("���º��ض���ʧ��");
				e.printStackTrace();
			}
		} else
			mv.setViewName("fail");
		return mv;
	}
}
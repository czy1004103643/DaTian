package cn.edu.bjtu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import cn.edu.bjtu.dao.BaseDao;
import cn.edu.bjtu.dao.SubAccountDao;
import cn.edu.bjtu.service.SubAccountService;
import cn.edu.bjtu.util.HQLTool;
import cn.edu.bjtu.util.IdCreator;
import cn.edu.bjtu.vo.SubAccount;



@Service("subAccountServiceImpl")
/**
 * ���˻������ʵ�� 
 * 
 *
 */
public class SubAccountServiceImpl implements SubAccountService{
	
	@Resource
	HibernateTemplate ht;
	@Resource 
	SubAccountDao subAccountDao;
	@Resource 
	BaseDao baseDao;
	@Resource
	HQLTool hqltool;
	@Resource
	SubAccount subAccount;
	
	@Override
	/**
	 * ��ȡ���˻��б�
	 */
	public List getSubAccount(String userId) {
		// TODO Auto-generated method stub
		
		return subAccountDao.getSubAccount(userId);
	}
	
	@Override
	public List getFindSubAccount(String userId, String username){
		String sql="from SubAccount where hostAccountId='"+userId+"' ";
		System.out.println("name="+username);
		
		if(username.equals("�˻�����")){
			//����ʱ�������û�����
			System.out.println("name2="+username);
			username = "";
			System.out.println("name3="+username);
			sql+=" and username like '%"+username+"%' ";
		}
		else if(username.contains("-")){
			String[] temp=username.split("-");
			if (temp.length<=1);
			else{
				String hostAccountNam=temp[0];
				String usernam=temp[1];
				sql+=" and username like '%"+usernam+"%' and hostAccountName like '%"+hostAccountNam+"%'";
			}	
			
		}
		else sql+=" and username like '%"+username+"%' or hostAccountName like '%"+username+"%'";
		return subAccountDao.getFindSubAccount(sql);
	}
	
	@Override
	public SubAccount getSubAccountDetail(String id){
		
		return subAccountDao.getSubAccountDetail(id);
	}
	
	@Override
	public boolean changeStatus(String id){
		return subAccountDao.changeStatus(id);
	}
	
	@Override
	public boolean deleteSubAccount(String id){
		return subAccountDao.deleteSubAccount(id);
	}
	
	@Override
	public boolean insertSubAccount(String username,String password,String resourceManagement,
			String transactionManagement,String schemaManagement,
			String statisticsManagement,String remarks,
			String hostAccountId,String hostAccountName){
		
		System.out.println("resourceManagement"+resourceManagement);
		System.out.println("transactionManagement"+transactionManagement);
		System.out.println("schemaManagement"+schemaManagement);
		System.out.println("statisticsManagement"+statisticsManagement);
		if(resourceManagement==null){
			resourceManagement = new String("��");}
		else if (resourceManagement.equals("on"))
		{resourceManagement = new String("��");}
		if(transactionManagement==null){
			transactionManagement = new String("��");}
		else if (transactionManagement.equals("on"))
		{transactionManagement = new String("��");}
		if(schemaManagement==null){
			schemaManagement = new String("��");}
		else if (schemaManagement.equals("on"))
		{schemaManagement = new String("��");}
		if(statisticsManagement==null){
			statisticsManagement = new String("��");}
		else if (statisticsManagement.equals("on"))
		{statisticsManagement = new String("��");}
		
		subAccount.setId(IdCreator.createSubAccountId());
		subAccount.setUsername(username);
		subAccount.setPassword(password);
		subAccount.setResourceManagement(resourceManagement);
		subAccount.setTransactionManagement(transactionManagement);
		subAccount.setSchemaManagement(schemaManagement);
		subAccount.setStatisticsManagement(statisticsManagement);
		subAccount.setRemarks(remarks);
		subAccount.setHostAccountId(hostAccountId);
		subAccount.setHostAccountName(hostAccountName);
		subAccount.setRelDate(new Date());
		subAccount.setStatus("����");
		
		return baseDao.save(subAccount);
	}
	
	@Override
	public boolean updateSubAccount(String id, String username,String password,String resourceManagement,
			String transactionManagement,String schemaManagement,
			String statisticsManagement,String remarks){
		
		System.out.println("resourceManagement"+resourceManagement);
		System.out.println("transactionManagement"+transactionManagement);
		System.out.println("schemaManagement"+schemaManagement);
		System.out.println("statisticsManagement"+statisticsManagement);
		if(resourceManagement==null){
			resourceManagement = new String("��");}
		else if (resourceManagement.equals("on"))
		{resourceManagement = new String("��");}
		if(transactionManagement==null){
			transactionManagement = new String("��");}
		else if (transactionManagement.equals("on"))
		{transactionManagement = new String("��");}
		if(schemaManagement==null){
			schemaManagement = new String("��");}
		else if (schemaManagement.equals("on"))
		{schemaManagement = new String("��");}
		if(statisticsManagement==null){
			statisticsManagement = new String("��");}
		else if (statisticsManagement.equals("on"))
		{statisticsManagement = new String("��");}
		
		subAccount = subAccountDao.getSubAccountDetail(id);// ����id���ҵ����˻�

		subAccount.setUsername(username);
		subAccount.setPassword(password);
		subAccount.setResourceManagement(resourceManagement);
		subAccount.setTransactionManagement(transactionManagement);
		subAccount.setSchemaManagement(schemaManagement);
		subAccount.setStatisticsManagement(statisticsManagement);
		subAccount.setRemarks(remarks);
		
		return baseDao.update(subAccount);
	}
	
}
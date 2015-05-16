package cn.edu.bjtu.service;

import java.util.List;

import cn.edu.bjtu.vo.Contract;

/**
 * 
 * @author RussWest0
 *
 */
public interface ContractService {
	public List getCompanyContract(String carrierId);

	public Contract getContractInfo(String contractId);

	public boolean insertContract(String id,String name, String caculateType,
			String carrierAccount, String startDate, String endDate,
			String contact, String phone, String remarks, String carrierId,
			String monthlyStatementDays,String path, String fileName);
	public boolean shutdownContract(String contractId,String reason);
	public List getFindContract(String carrierId,String startDate,String endDate,String name,int Display,int PageNow);
	public int getFindContractTotalRows(String carrierId,String startDate,String endDate,String name,int Display,int PageNow);
}
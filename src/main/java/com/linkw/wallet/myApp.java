package com.linkw.wallet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

/**  
* <p>Title: myApp.java</p>  
* <p>Description: 基于钱包基础的操作 </p> 
* <p>Copyright: Copyright (c) 2018</p>  
* <p>Company: www.smallcc.cn</p>  
* @author smallcc  
* @date 2018年7月3日  
* @version 1.0  
* @typename myApp
*/  
public class myApp {
	private Web3j web3;
	
	//进行一场交易
	public void transactioning(Integer from , Integer to ,BigInteger amount) throws Exception {
	//1.构建一个web3实例(工厂模式)
    web3=Web3j.build(new HttpService("http://localhost:8545"));
	//2.通过密码获取keystore的权限
    Credentials credentials=null;
    try {
		 credentials=WalletUtils.loadCredentials("Abc12345", WalletUtils.getDefaultKeyDirectory()+"\\UTC--2018-07-03T05-44-25.995000000Z--694f95579e3c85862c2281463799934c26a83b07.json");
	} catch (IOException | CipherException e) {
		throw new Exception("密码错误或文件加载失败");
	}
//	3.进行转账
    try {
    TransactionReceipt transferReceipt = Transfer.sendFunds(
       web3, credentials,//web3指定网络、credentials指定转出钱包账户
       "0x18f54aade5dde6ce3772b78b293d76c25d874f92",  // 将以太币发送到此账户
       BigDecimal.ONE,Convert.Unit.ETHER )
		       .send();
		}catch (Exception e){
		    e.printStackTrace();
		}
	}
	/**获取一个账户查询余额*/
    public BigInteger getBalance() throws InterruptedException, ExecutionException {
        web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
        EthGetBalance ethGetBalance = web3.ethGetBalance("0x0df14334e094acc0197d52a415d799c2b8a3b04b", DefaultBlockParameterName.LATEST).sendAsync().get();        
        BigInteger wei = ethGetBalance.getBalance();         
        System.out.println("balance is :" + wei);
        return wei;
    }


}

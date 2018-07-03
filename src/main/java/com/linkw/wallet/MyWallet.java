package com.linkw.wallet;

import java.io.File;
import java.io.IOException;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

/**  
* <p>Title: MyWallet.java</p>  
* <p>Description: 实现一个钱包的初始的内封装 </p> 
* <p>Copyright: Copyright (c) 2018</p>  
* <p>Company: www.smallcc.cn</p>  
* @author smallcc  
* @date 2018年7月3日  
* @version 1.0  
* @typename MyWallet
*/  
public class MyWallet {
	//声明一个类用于使用web的实例
	private static Web3j web3;
	public void getWeb3Version() throws Exception{
		//获取web3j的支持 现在是获取当前web3的版本
		HttpService hs=new HttpService("https://ropsten.infura.io/qGkprKCHIg1nRarZiHN4");
		web3=Web3j.build(hs);
		Web3ClientVersion web3ClientVersion=web3.web3ClientVersion().sendAsync().get();
		String web3Client = web3ClientVersion.getWeb3ClientVersion();
		System.out.println("the web3j version is :"+web3Client);
		
	}
	/**
	 * @param password
	 * @return 12 words 
	 * @throws Exception
	 */
	public String initWords(String password) throws Exception {
	String keyStoreDir = WalletUtils.getDefaultKeyDirectory();
    System.out.println("生成keyStore文件的默认目录：" + keyStoreDir);
    //通过密码及keystore目录生成钱包
    Bip39Wallet wallet;
	try {
		wallet = WalletUtils.generateBip39Wallet(password, new File(keyStoreDir));
	}catch(Exception e) {
		throw new Exception("文件路径出错");
	}
    //keyStore文件名
    System.out.println(wallet.getFilename());
    //12个单词的助记词
    return wallet.getMnemonic();
	}
	/**
	 * 由助记词生成私钥和公钥
	 * @throws IOException 
	 * */
	public void loadWalletByWords(String password) throws IOException,CipherException {
		String words="memory inmate first impulse syrup swift slight eye liberty slam obey push";
		Credentials	credentials=WalletUtils.loadBip39Credentials(password, words);//助记词的加载
		System.out.println("这个是钱包地址:"+credentials.getAddress());//获取钱包地址
		System.out.println("16位长度的公钥:"+credentials.getEcKeyPair().getPublicKey().toString(16));//获取公钥的16位表示
		System.out.println("16位长度的私钥:"+credentials.getEcKeyPair().getPrivateKey().toString(16));//获取私钥的16位表示
	}
	/**
	 * 加载keystroye文件获取公私密钥
	 * */
	public void loadWalletByKeyStore(String password) {
		String keyStoreDir = WalletUtils.getDefaultKeyDirectory();
		Credentials credentials = null;
		try {
			credentials = WalletUtils.loadCredentials(password, keyStoreDir+"\\UTC--2018-07-03T05-44-25.995000000Z--694f95579e3c85862c2281463799934c26a83b07.json");
		} catch (IOException | CipherException e) {
			try {
				throw new IOException("未找到keyStore文件");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				throw new CipherException("未找到keyStore文件");
			}catch(CipherException e2) {
				e2.printStackTrace();
			}
		}		
		System.out.println("这个是钱包地址:"+credentials.getAddress());//获取钱包地址
		System.out.println("16位长度的公钥:"+credentials.getEcKeyPair().getPublicKey().toString(16));//获取公钥的16位表示
		System.out.println("16位长度的私钥:"+credentials.getEcKeyPair().getPrivateKey().toString(16));//获取私钥的16位表示
	}
	
}

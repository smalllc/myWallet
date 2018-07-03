package com.linkw.wallet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

/**
 * <p>
 * Title: token.java
 * </p>
 * <p>
 * Description:代币的使用
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: www.smallcc.cn
 * </p>
 * 
 * @author smallcc
 * @date 2018年7月3日
 * @version 1.0
 * @typename token
 */
public class token {
	private String emptyAddress;

	/**
	 * 通过代币来查询
	 * 
	 * @param web3j
	 * @param fromAddress
	 * @param contractAddress
	 * @return BigInteger
	 * @throws IOException
	 */
	public static BigInteger getTokenBalance(Web3j web3j, String fromAddress, String contractAddress)
			throws IOException {
		String methodName = "balanceOf";
		List inputParameters = new ArrayList<>();
		List outputParameters = new ArrayList<>();
		Address address = new Address(fromAddress);
		TypeReference typeReference = new TypeReference() {
		};
		outputParameters.add(address);
		outputParameters.add(typeReference);
		// 构建一个方法
		Function function = new Function(methodName, inputParameters, outputParameters);
		String data = FunctionEncoder.encode(function);
		// 使用eth的合约创建
		Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress, data);
		EthCall ethCall;
		BigInteger balanceValue = BigInteger.ZERO;
		try {
			ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
			List results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			balanceValue = (BigInteger) results.get(0);
		} catch (IOException e) {
			throw new IOException("授权失败");
		}
		return balanceValue;
	}

	/**
	 * 查询代币名称
	 * 
	 * @param web3
	 * @param contractAddress
	 * @return String name
	 * @throws Exception
	 */
	public String getTokenName(Web3j web3, String contractAddress) throws Exception {
		String methodName = "name";
		String name = null;
		String formAddress = emptyAddress;
		List inputParameters = new ArrayList<>();
		List outputParameters = new ArrayList<>();

		TypeReference typeReference = new TypeReference() {
		};
		outputParameters.add(typeReference);

		Function function = new Function(methodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(formAddress, contractAddress, data);

		EthCall ethCall;
		try {
			ethCall = web3.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			name = results.get(0).toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * 查询代币符号
	 * 
	 * @param web3j
	 * @param contractAddress
	 * @return
	 */
	public String getTokenSymbol(Web3j web3j, String contractAddress) {
		String methodName = "symbol";
		String symbol = null;
		String fromAddr = emptyAddress;
		List inputParameters = new ArrayList<>();
		List outputParameters = new ArrayList<>();

		TypeReference typeReference = new TypeReference() {
		};
		outputParameters.add(typeReference);

		Function function = new Function(methodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

		EthCall ethCall;
		try {
			ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			symbol = results.get(0).toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return symbol;
	}

	/**
	 * 查询代币精度
	 *
	 * @param web3j
	 * @param contractAddress
	 * @return
	 */
	public int getTokenDecimals(Web3j web3j, String contractAddress) {
		String methodName = "decimals";
		String fromAddr = emptyAddress;
		int decimal = 0;
		List inputParameters = new ArrayList<>();
		List outputParameters = new ArrayList<>();

		TypeReference typeReference = new TypeReference() {
		};
		outputParameters.add(typeReference);

		Function function = new Function(methodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

		EthCall ethCall;
		try {
			ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			decimal = Integer.parseInt(results.get(0).toString());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return decimal;
	}
	/**
	 * 查询代币发行总量
	 *
	 * @param web3j
	 * @param contractAddress
	 * @return
	*/
	public  BigInteger getTokenTotalSupply(Web3j web3j, String contractAddress) {
	    String methodName = "totalSupply";
	String fromAddr = emptyAddress;
	BigInteger totalSupply = BigInteger.ZERO;
	List inputParameters = new ArrayList<>();
	List outputParameters = new ArrayList<>();

	TypeReference typeReference = new TypeReference() {
	    };
	outputParameters.add(typeReference);

	Function function = new Function(methodName, inputParameters, outputParameters);

	String data = FunctionEncoder.encode(function);
	Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

	EthCall ethCall;
	    try {
	        ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
	List results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
	totalSupply = (BigInteger) results.get(0);
	} catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	}
	return totalSupply;
	}

	
	/**
	 * 代币转账
	 * @param fromAddress
	 * @param password
	 * @param toAddress
	 * @param contractAddress
	 * @param amount
	 * @return
	 */
	public  String sendTokenTransaction(String fromAddress, String password, String toAddress, String contractAddress, BigInteger amount) {
	    String txHash = null;
	    Admin admin=Admin.build(new HttpService());
	    Web3j web3j=Web3j.build(new HttpService());
	    try {
	        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(
	                fromAddress, password, BigInteger.valueOf(10)).send();
	        if (personalUnlockAccount.accountUnlocked()) {
	            String methodName = "transfer";
	List inputParameters = new ArrayList<>();
	List outputParameters = new ArrayList<>();

	Address tAddress = new Address(toAddress);

	Uint256 value = new Uint256(amount);
	inputParameters.add(tAddress);
	inputParameters.add(value);

	TypeReference typeReference = new TypeReference() {
	            };
	outputParameters.add(typeReference);

	Function function = new Function(methodName, inputParameters, outputParameters);

	String data = FunctionEncoder.encode(function);

	EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
	BigInteger nonce = ethGetTransactionCount.getTransactionCount();
	BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(5), Convert.Unit.GWEI).toBigInteger();

	Transaction transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, gasPrice,
	BigInteger.valueOf(60000), contractAddress, data);

	EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).sendAsync().get();
	txHash = ethSendTransaction.getTransactionHash();
	}
	    } catch (Exception e) {
	        e.printStackTrace();
	}

	return txHash;
	}

	/**
	 * 计算合约地址
	 *
	 * @param address
	 * @param nonce
	 * @return
	*/
	private static String calculateContractAddress(String address, long nonce) {
	//样例 https://ropsten.etherscan.io/tx/0x728a95b02beec3de9fb09ede00ca8ca6939bad2ad26c702a8392074dc04844c7
	byte[] addressAsBytes = Numeric.hexStringToByteArray(address);

	    byte[] calculatedAddressAsBytes =
	            Hash.sha3(RlpEncoder.encode(
	new RlpList(
	                            RlpString.create(addressAsBytes),
	RlpString.create((nonce)))));

	calculatedAddressAsBytes = Arrays.copyOfRange(calculatedAddressAsBytes,
	12, calculatedAddressAsBytes.length);
	String calculatedAddressAsHex = Numeric.toHexString(calculatedAddressAsBytes);
	    return calculatedAddressAsHex;
	}

}

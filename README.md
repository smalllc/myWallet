创建钱包的过程  本次使用的是java 按照java思路走
使用gradle创建一个java项目
使用依赖
dependencies {
//想更换版本请到mavenRespotiosy中搜索web3j-core
    implementation group: 'org.bitcoinj', name: 'bitcoinj-core', version: '0.14.6'
	compile group: 'org.web3j', name: 'core', version: '3.4.0'
}
首先获取web3j支持
		HttpService hs=new HttpService("https://ropsten.infura.io/qGkprKCHIg1nRarZiHN4");
		web3=Web3j.build(hs);
		Web3ClientVersion web3ClientVersion=web3.web3ClientVersion().sendAsync().get();
		String web3Client = web3ClientVersion.getWeb3ClientVersion();
		System.out.println("the web3j version is :"+web3Client);
//这是官方给的实例
Web3j web3j = Web3j.build(new HttpService(
                "https://rinkeby.infura.io/<your token>"));  // FIXME: Enter your Infura token here;
log.info("Connected to Ethereum client version: "+ web3j.web3ClientVersion().send().getWeb3ClientVersion());
//成功获取支持后就可以生成助记词了
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
其中用到的 默认地址是 
        if (osName.startsWith("mac")) {
            return String.format(
                    "%s%sLibrary%sEthereum", System.getProperty("user.home"), File.separator,
                    File.separator);
        } else if (osName.startsWith("win")) {
            return String.format("%s%sEthereum", System.getenv("APPDATA"), File.separator);
        } else {
            return String.format("%s%s.ethereum", System.getProperty("user.home"), File.separator);
        }
web3j提供了苹果和windows的支持
使用了MnemonicUtils的一个工具类来生成钱包和keystore文件
 byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);

        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
        ECKeyPair privateKey = ECKeyPair.create(sha256(seed));
首先生成一个随机的128为hax熵生成一个种子send 然后send通过哈希算法生成一个12个单词的助记词drip supreme yard visual interest cannon any length cement tribe kid panther
通过密码和助记词生成公私密钥
memory inmate first impulse syrup swift slight eye liberty slam obey push
//其中涉及到的一个通过代币来寻找交易的信息 喜欢的朋友可以看一下







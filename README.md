����Ǯ���Ĺ���  ����ʹ�õ���java ����java˼·��
ʹ��gradle����һ��java��Ŀ
ʹ������
dependencies {
//������汾�뵽mavenRespotiosy������web3j-core
    implementation group: 'org.bitcoinj', name: 'bitcoinj-core', version: '0.14.6'
	compile group: 'org.web3j', name: 'core', version: '3.4.0'
}
���Ȼ�ȡweb3j֧��
		HttpService hs=new HttpService("https://ropsten.infura.io/qGkprKCHIg1nRarZiHN4");
		web3=Web3j.build(hs);
		Web3ClientVersion web3ClientVersion=web3.web3ClientVersion().sendAsync().get();
		String web3Client = web3ClientVersion.getWeb3ClientVersion();
		System.out.println("the web3j version is :"+web3Client);
//���ǹٷ�����ʵ��
Web3j web3j = Web3j.build(new HttpService(
                "https://rinkeby.infura.io/<your token>"));  // FIXME: Enter your Infura token here;
log.info("Connected to Ethereum client version: "+ web3j.web3ClientVersion().send().getWeb3ClientVersion());
//�ɹ���ȡ֧�ֺ�Ϳ����������Ǵ���
String keyStoreDir = WalletUtils.getDefaultKeyDirectory();
System.out.println("����keyStore�ļ���Ĭ��Ŀ¼��" + keyStoreDir);
//ͨ�����뼰keystoreĿ¼����Ǯ��
Bip39Wallet wallet;
try {
		wallet = WalletUtils.generateBip39Wallet(password, new File(keyStoreDir));
	}catch(Exception e) {
		throw new Exception("�ļ�·������");
	}
    //keyStore�ļ���
    System.out.println(wallet.getFilename());
    //12�����ʵ����Ǵ�
    return wallet.getMnemonic();
�����õ��� Ĭ�ϵ�ַ�� 
        if (osName.startsWith("mac")) {
            return String.format(
                    "%s%sLibrary%sEthereum", System.getProperty("user.home"), File.separator,
                    File.separator);
        } else if (osName.startsWith("win")) {
            return String.format("%s%sEthereum", System.getenv("APPDATA"), File.separator);
        } else {
            return String.format("%s%s.ethereum", System.getProperty("user.home"), File.separator);
        }
web3j�ṩ��ƻ����windows��֧��
ʹ����MnemonicUtils��һ��������������Ǯ����keystore�ļ�
 byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);

        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
        ECKeyPair privateKey = ECKeyPair.create(sha256(seed));
��������һ�������128Ϊhax������һ������send Ȼ��sendͨ����ϣ�㷨����һ��12�����ʵ����Ǵ�drip supreme yard visual interest cannon any length cement tribe kid panther
ͨ����������Ǵ����ɹ�˽��Կ
memory inmate first impulse syrup swift slight eye liberty slam obey push
//�����漰����һ��ͨ��������Ѱ�ҽ��׵���Ϣ ϲ�������ѿ��Կ�һ��







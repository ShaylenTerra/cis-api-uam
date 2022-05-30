# CIS-UAM

> Cadestal Information System 


## Very first steps
- git clone https://bitbucket.org/harvesting/cis-api-uam.git
- gradle clean asciidoctor

After that, see dev-guide under path build/docs

## Documentation

- First steps and general project structure see dev-guide
- All tasks and issues are being processed via bitbucket
- For general terms of contribution, see [Contribution Guide](CONTRIBUTING.md)

## Generate JKS Java KeyStore File

keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore jwt.jks -storepass mypass

## Export Public Key

keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey

We take only our Public key and copy it to our resource server src/main/resources/public.txt:


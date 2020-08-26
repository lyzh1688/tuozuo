import { JSEncrypt } from 'jsencrypt'

export default {
  rsaData (data, PUBLIC_KEY) {
    const jsencrypt = new JSEncrypt()
    jsencrypt.setPublicKey(PUBLIC_KEY)
    const result = jsencrypt.encrypt(data)
    return result
  },
  decodeData (data, privateKey) {
    const decryptor = new JSEncrypt()
    decryptor.setPrivateKey(privateKey)
    const result = decryptor.decrypt(data)
    return result
  }
}

package com.baozi.linfeng.encrypt

import com.baozi.linfeng.encrypt.des.Des

open class EncryptInterceptor(private val privateKey: String) {
    /**
     * 加密
     *
     * @param data 加密内容
     * @return 密文
     */
    open fun encode(data: String?): String? {
        data ?: return data
        //DES
        return Des.encode(privateKey, data)
    }

    /**
     * 解密
     *
     * @param data 密文
     * @return 内容
     */
    open fun decode(data: String?): String? {
        data ?: return data
        return Des.decode(privateKey, data)
    }

}
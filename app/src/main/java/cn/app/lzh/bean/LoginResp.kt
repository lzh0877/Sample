package cn.app.lzh.bean

/**Created by LZH On 2019/3/19*/
data class LoginResp(val code: Int, val message: String,
                     val token: String, val user: User, val expiretime: String
)
package pocClientesCreditas.controller.request

import org.apache.tomcat.util.codec.binary.Base64
import org.apache.tomcat.util.codec.binary.StringUtils
import org.springframework.web.multipart.MultipartFile

data class WebClientSendPdf(
    var pdf: String? = null
) {
    fun toBase64(file: MultipartFile): WebClientSendPdf {
        val sb = StringBuilder()
        sb.append("data:application/pdf;base64,")
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(file.bytes, false)))

        this.pdf = sb.toString()
        return this
    }
}
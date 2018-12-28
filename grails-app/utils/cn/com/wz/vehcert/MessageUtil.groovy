package cn.com.wz.vehcert

import org.springframework.context.MessageSourceResolvable
import org.springframework.context.NoSuchMessageException
import org.springframework.web.servlet.support.RequestContextUtils

/**
 * @Description:  重写了message标签 使其能在service中使用
 * @Create: 13-8-17 上午10:02   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class MessageUtil {
    static Closure message = { attrs ->
        messageImpl(attrs)
    }

    static messageImpl(attrs) {
        def messageSource = attrs.grailsAttributes.applicationContext.messageSource
        def locale = attrs.locale ?: RequestContextUtils.getLocale(attrs.request)

        def text
        def error = attrs.error ?: attrs.message
        if (error) {
            try {
                text = messageSource.getMessage(error , locale)
            }
            catch (NoSuchMessageException e) {
                if (error instanceof MessageSourceResolvable) {
                    text = error?.code
                }
                else {
                    text = error?.toString()
                }
            }
        }
        else if (attrs.code) {
            def code = attrs.code
            def args = attrs.args
            def defaultMessage
            if (attrs.containsKey('default')) {
                defaultMessage = attrs['default']
            } else {
                defaultMessage = code
            }

            def message = messageSource.getMessage(code, args == null ? null : args.toArray(),
                    defaultMessage, locale)
            if (message != null) {
                text = message
            }
            else {
                text = defaultMessage
            }
        }
        if (text) {
            return attrs.encodeAs ? text."encodeAs${attrs.encodeAs}"() : text
        }
        ''
    }
}

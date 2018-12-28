package cn.com.wz.parent.ui

import cn.com.wz.parent.ui.OmUiException
import grails.converters.JSON

import org.apache.commons.codec.digest.DigestUtils

/**
 * 用于支持封装Jquery控件的类
 * 一些通用的方法
 * @author haojia
 *
 */
class JqueryTagLibService {
    static transactional = false
    def grailsApplication
    
    def badJSChars = [':','-']
    def getUniqueId = { 'c_' + DigestUtils.md5Hex(UUID.randomUUID().toString()) }

    // sets up some default values given, and translates attribute values in javascript-compatible structures so they
    // can be used as config objects in YUI
    def establishDefaultValues(defaults, attrs) {
        def result = [:]
        // clone the attrs with boolean substitution
        attrs.each {key, val ->
            if (val instanceof Boolean || (val != null && val)) {
                result."$key" = makeJavascriptFriendly(val)
            }
        }
        defaults.each { key, val ->
            if (result."$key" == null) result."$key" = val
        }
        return result
    }

    // this will require some values to be present, or else throw an exception
    def establishDefaultValues(defaults, attrs, requirements) {
        requirements.each {
            if (!attrs."$it") {
                if (it.contains('|')) {
                    def valid = false
                    it.split('\\|').each { splitVal ->
                        if (attrs."$splitVal") valid = true
                    }
                    if (!valid) {
                        throw new OmUiException("Attribute ${it.split('\\|').join(' or ')} is required.")
                    }
                } else {
                    throw new OmUiException("Attribute $it is required.")
                }
            }
        }
        establishDefaultValues defaults, attrs
    }

    def processShortcutSyntax(defs, keyName, labelName, possibleKeys, description) throws OmUiException {
        defs.collect { configMap ->
            def keys = configMap.keySet()
            // use the key as the label if there is a key, but no label
            if (keyName in keys && !(labelName in keys)) {
                configMap."$labelName" = configMap."$keyName"
                keys = configMap.keySet()
            }
            if (!(keyName in keys) || !(labelName in keys)) {
                // find the entry that is not a possibleKey
                def shortcutKey = keys.find { !(it in possibleKeys) }
                if (!shortcutKey) {
                    throw new OmUiException("Incomplete definitions for GrailsUI ${description.name}: ${description.tagName} must include either a $keyName and $labelName, or a shortcut $keyName:\'$labelName\' within its column definitions.")
                }
                def shortcutLabel = configMap[shortcutKey]
                // remove the shortcut
                configMap.remove(shortcutKey)
                // interpret the shortcut
                configMap."$keyName" = shortcutKey
                configMap."$labelName" = shortcutLabel
            }
            configMap
        }
    }

    private def makeJavascriptFriendly(val) {
        if (!(val instanceof String)) return val
        if (val.isInteger()) return val.toInteger()        
        if (val.matches(/[Tt][Rr][Uu][Ee]/)) return true
        if (val.matches(/[Ff][Aa][Ll][Ss][Ee]/)) return false
        if (val instanceof Map) return '{' + mapToConfig(val) + '}'
        if (val instanceof List) return '[' + listToConfig(list) + ']'
        val
    }

    def dateToJs(date) {
        def cal = new GregorianCalendar()
        cal.time = date
        def yr = cal.get(Calendar.YEAR)
        def mo = cal.get(Calendar.MONTH)
        def d = cal.get(Calendar.DAY_OF_MONTH)
        def hr = cal.get(Calendar.HOUR_OF_DAY)
        def m = cal.get(Calendar.MINUTE)
        def s = cal.get(Calendar.SECOND)
        "%new Date($yr, $mo, $d, $hr, $m, $s)%"
    }
    
    /**
     * Any string could be sent in as the id, so before using it as a javascript identifier, we need to ensure some
     * characters are replaced
     */
    def toJS(s) {
        badJSChars.each {
            s = s.replaceAll(it,'_')
        }
        s
    }
    
    /**
     * Turns map into a javascript config object string for YUI.  This is what allows configurations passed into the
     * grailsUI tag to be passed along to the YUI widget.
     */
    def mapToConfig(attrs) {
        attrs.collect { key, val ->
            // if this is a handler, the val will be a javascript function, so don't quote it
            if (key == 'handler') return "$key: $val"
            "'$key': ${valueToConfig(val)}"
        }.join(",\n")
    }

    def listToConfig(list) {
        list.collect {
            if (it instanceof Map) {
                return "{" + mapToConfig(it) + "}"
            }
            valueToConfig it
        }.join(", ")
    }
    
    def collectionToConfig(collection) {
        if (collection instanceof Map) {
            return mapToConfig(collection)
        } else {
            return listToConfig(collection)
        }
    }

    def valueToConfig(val) {
        if (val instanceof String || val instanceof GString || val instanceof CharSequence) {
            // if this string resolves to a number, also don't quote it
            if (val.isNumber()) return val
            // if string starts with '@', that means this is a javascript object reference, and we shouldn't quote it
            if (val.startsWith('@')) return val - '@'
            return "'$val'"
        } else if (val instanceof Map) {
            return "{${mapToConfig(val)}}"
        } else if (val instanceof List) {
            return "[${listToConfig(val)}]"
        }
        val
    }

}

package cn.com.wz.parent.test
/**
 * 测试自定义标签
 * @author haojia
 *
 */
class SimpleTagLib {
	static namespace="s"
	def simple = {attrs,body->
			def js = g.javascript(body:"""
						alert('haojia');
					""").toString()
			
			println(js)
			out << """
						<script type="text/javascript">
							alert('asdf');
						</script>
					"""
			
			out << 'haojia'
		}
}

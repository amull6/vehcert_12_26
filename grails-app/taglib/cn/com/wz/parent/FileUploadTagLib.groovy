package cn.com.wz.parent
/**
 *@Description 自定义与文件上传相关的标签库
 *上传后回显的处理文件信息规则如：saveFileName0:;:fileName0:;:relativeFile0:_:saveFileName1:;:fileName1:;:relativeFile1:_:
 *@Auther huxx
 *@createTime 2012-9-22 上午10:57:00
 */
class FileUploadTagLib {
    //定义标签名
    static namespace='c'

    /**
     *@Description 添加、删除文件的相应处理
     *@params
     *@author huxx
     *@CreateTime 2012-10-10
     */
    def fileUpload1={attrs,body->
        def id =attrs.id

        def outStr='if(responseJSON.success){'+
                'var names=$("#'+id+'_uploadFileNames").val();'+
                'names+=responseJSON.saveFileName+":_:";'+
                'var saveFileName=responseJSON.saveFileName.split(":;:")[0];'+
                '$("#'+id+'_uploadFileNames").val(names);'+
                'var message=$("#'+id+'_message").html()+'+"'<div id="+'"'+id+"'+saveFileName+'"+'" >'+"'+'<input id="+'"'+id+'_hidden_'+"'+saveFileName+'"+'" type="hidden" value="'+"'+responseJSON.saveFileName+'"+'"/>'+"'+fileName+'<a href="+'"javaScript:void(0);" style="margin-top:5px;margin-left:5px;" class="btn_basic blue_black"  onclick="'+id+'_deleteDiv('+"'+"+'"'+"'"+'"+saveFileName+"'+"'"+'"+'+"')"+'">删除</a></div><br/>'+"';"+
                '$("#'+id+'_message").html(message);'+
                '}'

        out<< outStr
    }
    /**
     *@Description 处理文件上传列表
     *@params fileNames用于处理更新时，显示已经上传的文件列表。fileNames的形式为 保存名:;:文件名:;:文件路径
     *fileType用于表示处理文件的类型。fileType 的值可为 doc、image、video
     *@author huxx
     *@CreateTime 2012-10-10
     */
    def fileUpload2={attrs,body->
        def id =attrs.id
        def fileType=attrs.fileType

        //根据传入的已上传文件的名字和保存名称生成上传文件列表(保存名:;:文件名:;:文件路径)
        def fileNames=attrs.fileNames
        def uploadFileNames=''
        def messageTip=""
        def files=fileNames?.split(":_:")
        if(files){
            for(int i=0;i<files.length;i++){
                def name=files[i].split(":;:")
                if(name?.length==3){
                    def fileInfo=name[0]+':;:'+name[1]+':;:'+name[2]
                    uploadFileNames+=fileInfo +':_:'

                    messageTip+='<div id="'+id+name[0]+'"><input id="'+id+'_hidden_'+name[0]+'"  type="hidden" value="'+fileInfo+'"/>'+name[1]+'<a href="javaScript:void(0);" style="margin-top:5px;margin-left:5px;" class="btn_basic blue_black" onclick="'+id+"_deleteDiv('"+name[0]+"'"+')">删除</a></div><br/>'
                }
            }
        }

        def outStr='<input type="hidden" id="'+id+'_uploadFileNames" name="'+id+'_uploadFileNames" value="'+fileType+':_:'+uploadFileNames+'"><!-- 保存文件上传信息，第一个参数是上传类型，以后的是上传文件的名称 -->'+
                '<div id="'+id+'_message">'+messageTip+' </div>'+
                '<script type="text/javascript">'+
                ' function '+id+'_deleteDiv(divName){'+
                '	 if(confirm('+"'"+message(code:'fileUpload.deleted.label',default:'Are you sure to deleted?')+"')){"+
                '		 var my = document.getElementById("'+id+'"+divName);'+
                '			var deletePath=document.getElementById("'+id+'_hidden_"+divName).value;'+
                '			var deletes=deletePath.split(":;:");'+
                '			var relativeFile=deletes[2];'+
                '		    if (my != null){'+
                '		    	 my.parentNode.removeChild(my);'+
                '			}'+
                '	    	var fileNames=$("#'+id+'_uploadFileNames").val();'+
                '		  	fileNames=fileNames.replace(deletePath+'+"':_:','');"+
                '		  	$("#'+id+'_uploadFileNames").val(fileNames);'+
                '			var deleteUrl='+"'"+createLink(controller:'uploadr',action:'deleteFile')+"';"+
                '			$.post(deleteUrl,{"relativeFile":relativeFile},function(data,textStatus){'+
                '			},'+"'text');"+
                '	 }'+
                '}'+
                ' </script>'


        out<< outStr
    }
}

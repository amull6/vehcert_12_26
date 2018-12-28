<!doctype html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'articleCategory.label', default: 'Article Category')}" />
    <g:set var="entityName_value" value="${message(code: 'article.label', default: 'Article')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <script src="${resource(dir:"js",file:"parent/DivDialog.js") }" type="text/javascript" ></script>
    <script src="${resource(dir:"js",file:"parent/home.js") }" type="text/javascript" ></script>
    <style type="text/css">
        #article li{
             line-height:25px; padding:0 0 0 25px; list-style:none;
            background: url(../images/parent/dot.png) no-repeat 7px 9px;

         }
        #article li span{
            line-height:25px;
        }
        #article li a{
            line-height:25px;
            color:#069;text-decoration:none;
        }
        #newProduct li{
            line-height:25px; padding:0 0 0 25px; list-style:none;
            background: url(../images/parent/dot.png) no-repeat 7px 9px;
        }
        #newProduct li span{
            line-height:25px;
        }
        #newProduct li a{
            line-height:25px;
            color:#069;text-decoration:none;
        }
        #more_article{padding-left:500px;}
        #more_article a{
            color: #069;
        }
    </style>
</head>
<body>
<div id="div_wrap"></div>
<div id="page" style="background-color:white;margin:5px 5px">

    <div id="west-panel" style="margin_left:5px;">
        <div id="article" ></div>
        <div id="more_article" class='more' > <a href="javascript:void(0);">更多>></a></div>
    </div>

    <div id="center-panel" >
        <div style="margin-right:8px;margin-top:8px;">
            <div id="newProduct" style="border:0px"></div>
            <div id="more_newProduct" class='more' > <a href="javascript:void(0);">更多>></a></div>
        </div>
    </div>


</div>

<script>
    $(function() {
        /*getItAndProArticles('article',6,"${createLink(controller:'article',action:'getArticles')}",10,'${createLink(controller:'article',action:'frontShow')}',"${createLink(controller:'article',action:'toMoreArticles')}?max=10",1);
        getItAndProArticles('newProduct',6,"${createLink(controller:'article',action:'getArticles')}",10,'${createLink(controller:'article',action:'frontShow')}',"${createLink(controller:'article',action:'toMoreArticles')}?max=10",1);*/
        welcome(0,2,'article',"${createLink(controller:'article',action:'getArticles')}",5,"${createLink(controller:'article',action:'toMoreArticles')}?max=10",'${createLink(controller:'article',action:'frontShow')}',32,'article','more_article','1');
        welcome(1,2,'newProduct',"${createLink(controller:'article',action:'getArticles')}",5,"${createLink(controller:'article',action:'toMoreArticles')}?max=10",'${createLink(controller:'article',action:'frontShow')}',32,'newProduct','more_newProduct','1');
        $('#page').css({height:$(document).height()-16});
        //布局
        $('#page').omBorderLayout({
            panels:[{
                id:"west-panel",
                title:"<span '>通知通告</span>",
                region:"west",
                width:'48%'
            },{
                id:"center-panel",
                header:true,
                region:"center",
                title:'新产品公告',
                width:'48%'

            }]
        });
    });
    function showTopTip(content){
        $.omMessageTip.show({
            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
            content : content,
            timeout : 3000
        });
    }
</script>
</body>
</html>

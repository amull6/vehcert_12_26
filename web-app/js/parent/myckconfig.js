/*
 Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config )
{
    // Define changes to default configuration here. For example:
//    config.language = 'fr';
//    config.uiColor = 'red';
    //界面的语言配置?设置为'zh-cn'即可
    config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+config.font_names;
    config.defaultLanguage='zh-cn'
    //默认的字体名?plugins/font/plugin.js
//    config.font_defaultLabel='宋体';

    //定义ckeditor的工具栏
    config.toolbar = 'defaultToolbar';
    config.toolbar_defaultToolbar =
        [
            { name: 'document', items : [ 'Source','Preview','Templates' ] },
            { name: 'clipboard',   items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
            { name: 'links',       items : [ 'Link','Unlink','Anchor' ] },
            { name: 'insert',      items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak' ] },
            { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
            { name: 'tools',       items : [ 'Maximize','ShowBlocks','About' ] },
            { name: 'paragraph',   items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
            { name: 'styles',      items : [ 'Styles','Format','Font','FontSize' ] },
            { name: 'colors',      items : [ 'TextColor','BGColor' ] }

        ];
    config.toolbar_simpleToolbar =
        [
            { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
            { name: 'styles',      items : [ 'Styles','Format','Font','FontSize' ] },
            { name: 'colors',      items : [ 'TextColor','BGColor' ] },
            { name: 'tools',       items : [ 'Maximize','ShowBlocks','About' ] }

        ];
    config.toolbar_noToolbar =
        [

        ];
    //移除状态栏中的元素路径
    config.removePlugins = 'elementspath';
    //移除状态栏
    config.resize_enabled = false;
    config.enterMode = CKEDITOR.ENTER_P;
    config.shiftEnterMode = CKEDITOR.ENTER_BR;
    // 编辑器的z-index值
    config.baseFloatZIndex = 90000;

};


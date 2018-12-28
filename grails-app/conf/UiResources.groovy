modules = {
    'jquery' {
        resource id:'js', url:'js/parent/jquery-1.7.2.min.js',disposition:'head', nominify: true
    }
    'om'{
        dependsOn 'jquery'
        resource id:'css',url:'css/parent/om/apusic/om-all.css',disposition:'head'
        resource id:'js',url:'js/parent/operamasks-ui.min.js'
        resource id:'js',url:'js/parent/om/om-suggestion.js'
    }

}
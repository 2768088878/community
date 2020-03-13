function my$(id){
    return document.getElementById(id);
}

/*评论*/
function post(){
    var content = $("#comment_content").val();
    var question_id = $("#question_id").val();
    if (!content.trim()){
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            // "commentator":commentator,
            "parentId":question_id,
            "content":content,
            "type":1
        }),
        success:function (result) {
            if (result=='ok'){
                // $("#comment_section").hide();  隐藏输入框
                window.location.reload();//刷新页面
            }else if (result=='error'){
                $("#comment_msg").addClass("alert alert-danger").html("请先登录");
                var isAccept=confirm("你是否要登录？")
                if(isAccept){
                    window.open("https://github.com/login/oauth/authorize?client_id=b5d45514c04c82bf47f4&redirect_uri=http://localhost:8087/callback&scope=user&state=1");
                }
            }
        },
        dataType:"json"
    });
}


/*回复*/
function reply(e){
    var commentId = e.getAttribute("data-id");
    var replyContent = $("#replyId-"+commentId).val();
    if (!replyContent.trim()){
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":commentId,
            "content":replyContent,
            "type":2
        }),
        success:function (result) {
            if (result=='ok'){
                // $("#comment_section").hide();  隐藏输入框
                window.location.reload();//刷新页面
            }else if (result=='error'){
                $("#comment_msg").addClass("alert alert-danger").html("请先登录");
                var isAccept=confirm("你是否要登录？")
                if(isAccept){
                    window.open("https://github.com/login/oauth/authorize?client_id=b5d45514c04c82bf47f4&redirect_uri=http://localhost:8087/callback&scope=user&state=1");
                }
            }
        },
        dataType:"json"
    });
}

/*展开二级评论*/
function collapseComments(e) {
    // debugger;
    /*获取点击展开按钮上绑定的不同的id值*/
    var id=e.getAttribute("data-id");
    /*将获取的id值与comment拼接*/
    var comments = $("#comment-"+id);
    //获取一下二级评论展开状态
    if (comments.hasClass("in")){
        comments.removeClass("in");
        e.classList.remove("active")
    }else {
        var subCommentContainer=$("#comment-"+id);

        if (subCommentContainer.children().length!=1){
            /*展开二级评论*/
            comments.addClass("in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/"+id,function (data) {

                $.each(data,function (index,comment) {

                    var mediaLeftElement=$("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>",{
                        "class": "media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }));
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                /*展开二级评论*/
                comments.addClass("in");
                e.classList.add("active");
            });
        }
    }
}

function selectTag(e) {
    var value = e.getAttribute("data-tag")
    var previous = $("#tag").val();

    if (previous.indexOf(value)==-1){
        if (previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }

}


function showSelectTag() {
    $("#select-tag").show();
}




















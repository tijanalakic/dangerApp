$(document).ready(function() {
	 $('#image-upload').change(function (e) {
	        var files = [];
	        for (var i = 0; i < $(this)[0].files.length; i++) {
	            files.push($(this)[0].files[i].name);
	        }
	        $('#uploaded-images').text(files.join('; '));
	    });
});
$(document).ready(function() {
	$('#video-upload').change(function (e) {
	     if (e.target.files.length) {
	    	 $('#uploaded-video').text(e.target.files[0].name);
	     }
	});
});



$(document).on('click', '#comment-button', function(){ 
    $(this).parent().next().slideToggle();
   });

$(document).ready(function(){
	getAllPosts();
});
function getAllPosts(){
	
	var request=new XMLHttpRequest();
	request.onreadystatechange=function(){
		if((request.readyState==4) && (request.status==200)){
			var result=JSON.parse(request.responseText);
			displayPosts(result);
		}	
	};
	request.open("GET","?action=loadPost",true);
	request.send(null);
}

function displayPosts(posts){	
	
	for( var i=0;i<posts.length;i++){
		var post=posts[i];
		
		if(post.type==0){
			displayRSS(post);	
		}
		else{ 
			displayPost(post);
		}
	}
}

function displayRSS(post){
	var postList=document.getElementById("post-list");
	var postId = post.postId;	
	var postDiv = document.createElement("div");
	postDiv.id = "post-item-"+postId;
	var message="";
	message+=' <div class="col-md-12 grid-margin"><div class="card rounded">'+
	'<div class="card-header">'+
	 '   <div class="d-flex align-items-center justify-content-between">'+
	  '      <div class="d-flex align-items-center">'+
	            '<div class="ml-2">'+
	               ' <p><b><a href="'+post.link+'"target="_blank" style="color:blue">'+post.title+'</a></b></p>'+
	               ' <p class="tx-11 text-muted">'+post.date+'</p>'+
	           ' </div>'+
	        '</div>'+

	   ' </div>'+
	'</div>'+
	'<div class="card-body">'+
	    '<p class="mb-3 tx-14">'+post.message+'</p>'+
	  '  <img class="img-fluid" src="../../../assets/images/sample2.jpg" alt="">'+
	'</div>'+
	'</div>'+
	'</div>'+
	'</div>'+
	'</div>'+
	'</div>';
	postDiv.innerHTML+=message;

	if (postList.firstElementChild != null){					
		postList.insertBefore(postDiv,  postList.firstElementChild);
	} else {
		postList.appendChild(postDiv);
	}	
}
function submitPost(data){
	event.stopPropagation();
    event.preventDefault();

    var form = document.getElementById("addPost");
    var images = event.target.images.files;
    var video = event.target.video.files[0];
 
    var data = new FormData(form);

    postVideo = null;
	postImages = [];
    
    if (typeof images[0] !== "undefined"){
    	var i;
    	for (i = 0; i < images.length; i++){
    		
    	    let imagesReader = new FileReader();

    		imagesReader.onload = function (e) {
    	    	postImages.push(imagesReader.result);
    	    }
    		imagesReader.readAsDataURL(images[i]);
    	}
    } else if (typeof video !== "undefined"){
        var videoReader = new FileReader();

        videoReader.onload = function (e) {
        	postVideo = videoReader.result;
        }
        
		videoReader.readAsDataURL(video);
    }
    
    form.reset();
	document.getElementById("uploaded-images").innerHTML = "";
	document.getElementById("uploaded-video").innerHTML = "";

	postData(data);
}

function postData(data) {
	
	var request=new XMLHttpRequest();
	request.onreadystatechange=function(){
		if((request.readyState==4) && (request.status==200)){
			var result=JSON.parse(request.responseText);	
			displayPost(result);
		}	
	};
	request.open("POST","?action=post",true);
	request.send(data);
}

function displayPost(post){
	
	var postId = post.postId;	
	var postDiv = document.createElement("div");
	postDiv.id = "post-item-"+postId;
	var msg="";
	var postList=document.getElementById("post-list");

	msg+=' <div class="col-md-12 grid-margin"> <div  height="300" max-width="600" class="card rounded">'+
	'<div class="card-header">'+
	 '<div class="d-flex align-items-top">'+
	 '<img height="40" width="40" class="img-xs rounded-circle" src="'+post.postedUserProfilePicture+'" alt="">'+
	  '<div class="d-flex align-items-center">'+
	            '<div class="ml-2">'+
	               '   <p>'+post.postedUsername+'</p>'+
	               ' <p class="tx-11 text-muted">'+post.date+'</p>'+
	           ' </div>'+
	        '</div>'+

	   ' </div>'+
	'</div>'+
	'<div class="card-body">'+
	    '<p class="mb-3 tx-14">'+post.parsedMessage+'</p>';
		if (post.images != null){
			for (img of post.images[0]){	
				msg += 
						'<div class="text-center">'
							+ '<img src="' + img + '" class="img-fluid px-3 px-sm-4 mt-3 mb-4"/>'
						+ '</div>'		
			}
		}
		
		if (post.videos != null){
			for (video of post.videos){
				msg += 
					'<div class="embed-responsive embed-responsive-16by9">'
						+'<video autoplay loop muted class="embed-responsive-item">'
							+ '<source src="'+ video +'" type="video/mp4"/>'
						+ '</video>'
					+'</div>';
			}
		}
		
		if (post.youtube != null){
			for (youtubeVideo of post.youtube){
				msg += 
				'<div class="embed-responsive embed-responsive-16by9"> ' 
						+'<iframe class="embed-responsive-item" src="'+youtubeVideo+'" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"'
						 +'allowfullscreen>'
						+' </iframe>'
					+'</div>';
			}
		}
		
		if (post.links != null){
			for (link of post.links){
				msg += '<a href='+link+' class="text-primary" target="_blank">Link </a>';
			}
		}
	msg+='</div>'+
   ' <div class="card-footer">'+
  '  <div class="d-flex post-actions">'+
     '   <a target="_blank" href="https://www.facebook.com/sharer/sharer.php?&u=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2Fplugins%2F&amp;src=sdkpreparse"  class="d-flex align-items-center text-muted mr-4">'+
         '   <i class="fab fa-facebook-f"></i>'+

          '  <p class="d-none d-md-block ml-2">Facebook</p>'+
       ' </a>'+
                 '   <a href="https://twitter.com/intent/tweet?text='+ post.message +'"  target="_blank" class="d-flex align-items-center text-muted mr-4" data-show-count="false">'+
                   '     <i class="fab fa-twitter"></i><script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>'+

                  '      <p class="d-none d-md-block ml-2">Twitter</p>'+
                  '  </a>'+
                  '  <a  id="comment-button" href="javascript:void(0)" class="d-flex align-items-center text-muted">'+
                    '    <i class="far fa-comment-alt"></i>'+


                     '   <p class="d-none d-md-block ml-2" >Comment</p>'+
                  '</a>'+
                  
 
	'</div>'+
	 ' <div class="comment-insert" id="comment-insert" style="display:none">'+
    '<div class="card-body">'+
       ' <div class="comment">'+
      '<form id="add-comment'+post.postId+'" method="post" onsubmit="submitComment(event)">'+
           '<textarea class="form-control" name="comment" placeholder="Add comment..." rows="4"></textarea>'+
            '<input type="hidden" name="fullname" value="'+post.postedUsername+'">'+
            '<input type="hidden" name="postId" value="'+post.postId+'">'+
           ' <input type="hidden" name="proflilePicture" value="'+post.postedUserProfilePicture+'">'+
                '<div class="comment-options">'+
                    '<label class="ml-2" for="comment-image-upload"> <i class="fa fa-camera"></i>'+
                   ' </label> '+
					'<input type="file" id="comment-image-upload" name="image" style="display: none" accept="image/*" />'+
                    '<label id="comment-uploaded-image"></label>'+
                    '<button class="mt-2 btn btn-outline-primary float-right">Comment</button>'+
				'</div>'+
		'</form>'+
        '</div>'+
   ' </div>'+
'<div id="comment-list'+post.postId+'"></div>'+
	'</div>'+
	'</div>'+
	'</div>'+
	'</div>'+
'</div>'
	+'</div>';
	postDiv.innerHTML+=msg;

	if (postList.firstElementChild != null){					
		postList.insertBefore(postDiv,  postList.firstElementChild);
	} else {
		postList.appendChild(postDiv);
	}
	

	if (post.comments != null){
		var comments = post.comments[0];
		for(var i = 0; i < comments.length; i++) {
		    var comment = comments[i];
	
		  displayComment(comment);
		}
	}
}
$(document).ready(function() {
	 $('#comment-image-upload').change(function (e) {
	     if(e.target.files.length){
	    	 $('#comment-uploaded-image').text(e.target.files[0].name);
	     }
	    });
});


function submitComment(event){
	
	event.stopPropagation();
	event.preventDefault();
	var form=document.getElementById(event.target.id);
	var image=event.target.image.files[0];
	var data=new FormData(form);
	var postImage=null;
	if(typeof image !== "undefined"){
		var fileReader=new FileReader();
		fileReader.onload=function(e){
		postImage=fileReader.result;		
		}
		fileReader.readAsDataURL(image);
	}
	form.reset();
	document.getElementById("comment-uploaded-image".innerHTML="");
	postComment(data);
	
}

function postComment(data){
	
	var request=new XMLHttpRequest();
	request.onreadystatechange=function(){
		if((request.readyState==4)&&(request.status==200)){
			var result=JSON.parse(request.responseText);
			displayComment(result);
		}
	};
	request.open("POST","?action=comment",true);
	request.send(data);

	
}

function displayComment(comment){
	var commentList=document.getElementById("comment-list"+comment.postId);
	var commentDiv=document.createElement("div");

var content="";
content+='<div class="comment-show" id="comment-show">'+    
 '<div class="card-body">'+
    '<div class="d-flex align-items-center justify-content-between">'+
        '<div class="d-flex align-items-top">'+
            '<img height="32" width="32" class="img-xs rounded-circle" src="'+comment.profilePicture+'" alt="">'+
            '<div class="ml-2"> <p>'+comment.fullname+'</p>'+
            '<p class="mb-3 tx-14">'+comment.comment+'</p>'+
            '</div>'+
        '</div>'+
	'</div>'+
	 '<div class="ml-40">';
    
             if(comment.image!==null){
             content+='<img height="210" width="210" class="img-fluid" src="'+comment.image+'" alt="">';
             }
             content+='<p class="tx-11 text-muted">'+comment.date+'</p>'+
  	'</div>'+  
  '</div>'+     
'</div>';
  	commentDiv.innerHTML=content;
  	commentList.appendChild(commentDiv);
}
function refreshPostList(){
	var postList=document.getElementById("post-list");
	postList.innerHTML="";
	getAllPosts();
}

$(document).ready(function() {
	$('#add-danger').on('hidden.bs.modal', function () {
	    $('#add-danger form')[0].reset();
	    });
});

function initMap() {
	  
	  var start = {lat: 34.0522, lng:-118.2437};
	  // The map, centered at LA :) <3
	  var map = new google.maps.Map(
	      document.getElementById('map'), {zoom: 15, center: start});
	  // The marker, positioned at
	  var marker = new google.maps.Marker({position: start, map: map});

	  google.maps.event.addListener(map, 'click', function(event) {
		   marker.setPosition(event.latLng);
		   document.getElementById("lat").value = event.latLng.lat();
  	   document.getElementById("lon").value = event.latLng.lng();
		});
	}


$(document).ready(function(){
	  $('#close-add-danger').click(function(){
     $('#add-danger-form')[0].reset();
     
	  });
	});
$(document).ready(function(){
	setInterval('refreshPostList()',30000);
});

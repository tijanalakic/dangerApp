<%@ page import="net.etfbl.main.bean.UserBean"%>
<%@ page import="net.etfbl.main.dto.User"%>
<%@ page import="net.etfbl.main.dto.Danger" %>
<%@ page import="net.etfbl.main.bean.DangerBean" %>
<%@ page import="net.etfbl.main.dto.DangerCategory" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>  
<jsp:useBean id="user" type="net.etfbl.main.bean.UserBean" scope="session" />
<jsp:useBean id="dangerBean" type="net.etfbl.main.bean.DangerBean" scope="session" />
<!doctype html>
<html class="no-js" lang="zxx">
	
	<head>
		<meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<title>DangerApp | Home</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
		
		<!-- SCRIPTS -->
		<script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="assets/js/home.js" type="text/javascript"></script>
		<script src="assets/js/edit.js" type="text/javascript"></script>
		
		
		<!-- CSS here -->
		<link rel="stylesheet" href="assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="assets/css/owl.carousel.min.css">
		<link rel="stylesheet" href="assets/css/slicknav.css">
		<link rel="stylesheet" href="assets/css/flaticon.css">
		<link rel="stylesheet" href="assets/css/progressbar_barfiller.css">
		<link rel="stylesheet" href="assets/css/gijgo.css">
		<link rel="stylesheet" href="assets/css/animate.min.css">
		<link rel="stylesheet" href="assets/css/animated-headline.css">
		<link rel="stylesheet" href="assets/css/magnific-popup.css">
		<link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
		<link rel="stylesheet" href="assets/css/themify-icons.css">
		<link rel="stylesheet" href="assets/css/slick.css">
		<link rel="stylesheet" href="assets/css/nice-select.css">
		<link rel="stylesheet" href="assets/css/style.css">
		<link rel="stylesheet" href="assets/css/home.css">
	
	</head>
	
	<body onLoad="displayWeather('${user.user.countryCode}','${user.user.region}')" >
		<div id="fb-root"></div>
		<script async defer crossorigin="anonymous" src="https://connect.facebook.net/sr_RS/sdk.js#xfbml=1&version=v8.0" nonce="VzGMgJ0N"></script>
			
			<!-- ? Preloader Start -->
			<div id="preloader-active">
				<div
					class="preloader d-flex align-items-center justify-content-center">
					<div class="preloader-inner position-relative">
						<div class="preloader-circle"></div>
						<div class="preloader-img pere-text">
							<img src="assets/img/logo/loder.png" alt="">
						</div>
					</div>
				</div>
			</div>
			
			<!-- Preloader Start -->
			<div class="container">
				<div class="profile-page tx-13">
					<div class="row">
						<div class="col-12 grid-margin">
							<div class="profile-header">
								<div class="cover">
									<div class="gray-shade"></div>
									<figure>
										<img src="https://bootdey.com/img/Content/bg1.jpg"
											class="img-fluid" style="height: 250px;" alt="profile cover">
									</figure>
									<div
										class="cover-body d-flex justify-content-between align-items-center">
										<div>
											<img class="profile-pic" src="${user.user.profilePicture}"
												alt="profile"> <span class="profile-name">${user.user.fullName}</span>
										</div>
										<div class="d-none d-md-block">
											<form action="?action=logout" method="post">
												<button type=submit
													class="btn btn-primary btn-icon-text btn-edit-profile">
													<i class="fas fa-sign-out-alt"> </i> Logout
												</button>
											</form>
										</div>
									</div>
								</div>
								<div class="header-links">
									<ul class="links d-flex align-items-center mt-3 mt-md-0"></ul>
								</div>
							</div>
						</div>
					</div>
		
		
					<div class="row profile-body" >
		
						<!-- left wrapper start -->
		
						<div class="d-md-block col-md-4 col-xl-3 left-wrapper">
							<div class="card rounded">
								<div class="card-body">
		
		
									<div class="mt-3 border-bottom">
										<label class="tx-11 font-weight-bold mb-0 text-uppercase">Name:</label>
										<p class="text-muted">${user.user.fullName}</p>
									</div>
		
									<div class="mt-3 border-bottom">
										<label class="tx-11 font-weight-bold mb-0 text-uppercase">Number
											of logins:</label>
										<p class="text-muted">${user.user.loginCounter}</p>
									</div>
		
									<div class="mt-3 d-flex s">
										<button class="btn btn-outline-primary float-right" data-toggle="modal" data-target='#add-danger'>
											<i class="far fa-plus-square"></i>Add notification
										</button>
									</div>
									<div class="mt-3 border-top">
										<label class="tx-11 font-weight-bold mb-0 text-uppercase">Notifications:</label>
									</div>
							
									<% java.text.DateFormat df=new java.text.SimpleDateFormat("dd MMM yyyy HH:mm"); %>
									<%if(user.getUser().isAppNotification()){String color="blue"; %>
									<%for(Danger danger:dangerBean.getAll()){ %>
										<div class="mt-3 border-bottom">
										<% if (danger.isEmergency()){ %>
										<i style="color: red" class="fas fa-exclamation"></i>
										<% color="red";} else {color="blue";} %>
								
									<%for(String categoryName:danger.getCategories()){%>							
										<label style="color:<%=color %>" class="tx-11 font-weight-bold mb-0 text-uppercase">| <%= categoryName%></label>
										<%} %>
										<label style="color:<%=color %>" class="tx-11 font-weight-bold mb-0 text-uppercase">|</label>
										<p class="text-muted"><%=danger.getMessage() %> <br> <%=df.format(danger.getInsertDate())%> <br> added by:<%=danger.getPostedByFullName() %></p>
										</div>
									<%}}%>
									
								</div>
							</div>
						</div>
						
						<!-- left wrapper end -->
						
						<!-- middle wrapper start -->
		
						<div class="col-md-8 col-xl-6 middle-wrapper">
							<div class="row">
								<div style="height:220; max-width:600" class="col-md-12 grid-margin">
									<div class="card rounded">
										<div class="card-header">
											<div class="d-flex align-items-center justify-content-between">
												<div class="d-flex align-items-center">
													<img class="img-xs rounded-circle"
														src="${user.user.profilePicture}" alt="">
													<div class="ml-2">
														<p>${user.user.fullName}</p>
													</div>
												</div>
											</div>
										</div>
										
										<div class="card-body">
											<form method="post" onsubmit="submitPost(event)" id="addPost"
												enctype="multipart/form-data">
												<div class="post">
													<textarea class="form-control" name="message"
														placeholder="Post" rows="4"></textarea>
		
													<div class="post-options mt-3">
														<label for="image-upload"> <i class="fa fa-camera"></i>
															Photos |
														</label>
														 <input type="file" id="image-upload" name="images"
															style="display: none" accept="image/*" multiple />
															 <label	id="uploaded-images"></label>
															  <label for="video-upload">
															<i class="fas fa-video"></i> Video
														</label>
														 <input type="file" id="video-upload" name="video"
															style="display: none" accept="video/*" /> <label
															id="uploaded-video"></label>
														<button type="submit"
															class="btn btn-outline-primary float-right">Post</button>
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>
		
								<!-- POSTS start-->		
								<div id="post-list"></div>
								<!-- POSTS end-->	
								
							</div>
						</div>
						
						<!-- middle wrapper end -->
						
						<!-- right wrapper start -->
						<div class="d-xl-block col-xl-3 right-wrapper">
							<div class="row">
		
								<div class="col-md-12 grid-margin">
									<div class="card rounded">
										<div class="card-body" >
											<h2 class="card-title">weather forecast</h2>
											<div id="weather-card"></div>
		
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- right wrapper end -->
					</div>
				</div>
		
				<footer>
					<div class="footer-wrappper section-bg"
						data-background="assets/img/gallery/footer-bg.png">
		
						<!-- footer-bottom area -->
						<div class="footer-bottom-area">
							<div class="container">
								<div class="footer-border">
									<div class="row d-flex align-items-center">
										<div class="col-xl-12 ">
											<div class="footer-copy-right text-center">
												<p>
													<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
													Copyright &copy;
													<script>
														document.write(new Date()
																.getFullYear());
													</script>
													All rights reserved | This template is made with <i
														class="fa fa-heart" aria-hidden="true"></i> by <a
														href="https://colorlib.com" target="_blank">Colorlib</a>
													<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Footer End-->
					</div>
				</footer>
				
				</div>
				<!-- Modal -->
				<div class="modal fade" id="add-danger" tabindex="-1" role="dialog" aria-labelledby="add-dange-title" aria-hidden="true" onLoad="initMap()")>
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <label class="tx-12 font-weight-bold mb-0 text-uppercase">Add danger</label>
				        <button type="button" class="close" id="close-add-danger" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <form id="add-danger-form" class="add-danger-form" method="post" action="?action=danger">
				      	  <div class="modal-body">
				                <div class="form-group">
				                    <textarea class="form-control" id="message" name="message" placeholder="Message" required rows="3"></textarea>
				                </div>
				            
				                
				                 <div class="form-group row">
				                  <div class="col-sm-6 mb-3 mb-sm-0">
					              	 <select data-live-search="true" role="button" aria-haspopup="true" tabindex="0" id="categories" name="categories" multiple class="ignore multi-select-button" multiple>
					                     <% for (DangerCategory category : dangerBean.getDangerCategories()){ %>
					                     	<option value=<%=category.getDangerId() %>><%=category.getName() %></option>
					                     <%} %>
					                 </select>
				                  </div>
				                  <div class="col-sm-6">
			 						<div class="custom-control custom-checkbox small">
			                       		<input type="checkbox" class="custom-control-input tx-12" id="emergency-check" name="emergencyCheck" value="true">
			                        <label class="custom-control-label tx-12" for="emergency-check">Emergency</label>
			                      </div>
				                  </div>
				                  <input type="hidden" id="lat" name="lat">     
				                  <input type="hidden" id="lon" name="lon">
				                </div>
				    	  	</div>
				    	  	
				    	  	<div id="map" style="height: 400px;"></div>
				    	  	
						   	<div class="modal-footer">
						        <button type="submit" class="btn btn-primary" >Add danger</button>
						    </div>
			              </form>
		
				    </div>
				  </div>
				</div>
		
				<!-- Scroll Up -->
				<div id="back-top">
					<a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
				</div>
		 		 <script  async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqfL0xAfhW_PFJ4xTpFjPOCuxB0_QoQhA&callback=initMap"> </script>
				<!-- JS here -->
				<script src="./assets/js/vendor/modernizr-3.5.0.min.js"></script>
				<!-- Jquery, Popper, Bootstrap -->
				<script src="./assets/js/vendor/jquery-1.12.4.min.js"></script>
				<script src="./assets/js/popper.min.js"></script>
				<script src="./assets/js/bootstrap.min.js"></script>
				<!-- Jquery Mobile Menu -->
				<script src="./assets/js/jquery.slicknav.min.js"></script>
		
				<!-- Jquery Slick , Owl-Carousel Plugins -->
				<script src="./assets/js/owl.carousel.min.js"></script>
				<script src="./assets/js/slick.min.js"></script>
				<!-- One Page, Animated-HeadLin -->
				<script src="./assets/js/wow.min.js"></script>
				<script src="./assets/js/animated.headline.js"></script>
				<script src="./assets/js/jquery.magnific-popup.js"></script>
		
				<!-- Video bg -->
		   		 <script src="./assets/js/jquery.vide.js"></script>
		
				<!-- Date Picker -->
				<script src="./assets/js/gijgo.min.js"></script>
				<!-- Nice-select, sticky -->
				 <script src="./assets/js/jquery.nice-select.min.js"></script>
				<script src="./assets/js/jquery.sticky.js"></script>
				<script src="./assets/js/multiselect.min.js"></script>
				
				<!-- Progress -->
				<script src="./assets/js/jquery.barfiller.js"></script>
		
				<!-- counter , waypoint,Hover Direction -->
				<script src="./assets/js/jquery.counterup.min.js"></script>
				<script src="./assets/js/waypoints.min.js"></script>
				<script src="./assets/js/jquery.countdown.min.js"></script>
				<script src="./assets/js/hover-direction-snake.min.js"></script>
		
				<!-- contact js -->
				<script src="./assets/js/contact.js"></script>
				<script src="./assets/js/jquery.form.js"></script>
				<script src="./assets/js/jquery.validate.min.js"></script>
				<script src="./assets/js/mail-script.js"></script>
				<script src="./assets/js/jquery.ajaxchimp.min.js"></script>
		
				<!-- Jquery Plugins, main Jquery -->
				<script src="./assets/js/plugins.js"></script>
				<script src="./assets/js/main.js"></script>
				<script src="./assets/js/jquery.multi-select.min.js"></script>
		<script>
		$('#categories').niceSelect('destroy'); 
		
		$('#categories').multiSelect()
		
		</script>
	</body>
	
</html>
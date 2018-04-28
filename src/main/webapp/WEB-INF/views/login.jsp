
<html lang="en">
    <head>
    <meta name="google-signin-scope" content="profile email">
	  <meta name="google-signin-client_id" content="29518267527-r4dd50mjsjb5qoa7be1agcrie53rg13i.apps.googleusercontent.com">
	  <script src="https://apis.google.com/js/platform.js" async defer></script>
	</head>
	<body>
	  <form action="/login" method="post">
        <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark">connecting....</div>
      </form>
	  
	  <script src="https://apis.google.com/js/platform.js" async defer></script>
	  <script>
		function onSignIn(googleUser) {
		  // Useful data for your client-side scripts:
		  var profile = googleUser.getBasicProfile();
		  console.log("ID: " + profile.getId()); // Don't send this directly to your server!
		  var id_token = googleUser.getAuthResponse().id_token;
		    console.log("ID Token: " + id_token);
		    var form = document.createElement("form");
		    form.setAttribute("method", "post");
		    form.setAttribute("action", "/home");
		    var hiddenField = document.createElement("input");
		    hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "name");
            hiddenField.setAttribute("value", profile.getName());
            hiddenField.setAttribute("name", "token");
            hiddenField.setAttribute("value", id_token);
            form.appendChild(hiddenField);
		    document.body.appendChild(form);
		    form.submit();
		};
	  </script>
	</body>
</html>
	  
importScripts('https://www.gstatic.com/firebasejs/7.13.2/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/7.13.2/firebase-messaging.js')
//importScripts('https://www.gstatic.com/firebasejs/7.13.2/firebase-analytics.js')

// Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyBBnC6XZOkQnlQT3qRR_bVQZgNEB52b1E4",
    authDomain: "test01-c6e32.firebaseapp.com",
    databaseURL: "https://test01-c6e32.firebaseio.com",
    projectId: "test01-c6e32",
    storageBucket: "test01-c6e32.appspot.com",
    messagingSenderId: "374754601068",
    appId: "1:374754601068:web:fa680883b622a99aabd049",
    measurementId: "G-RK17HJE357"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  //firebase.analytics();
  
  const messaging = firebase.messaging();
  
  messaging.setBackgroundMessageHandler(function(payload) {
	  console.log('[firebase-messaging-sw.js] Received background message ', payload);
	  // Customize notification here
	  const notificationTitle = 'Background Message Title';
	  const notificationOptions = {
	    body: 'Background Message body.',
	    icon: '/firebase-logo.png'
	  };

	  return self.registration.showNotification(notificationTitle,
	    notificationOptions);
	});
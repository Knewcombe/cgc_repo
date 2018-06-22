$(document).ready(function() {
	document.getElementById("share_button").addEventListener("click", shareEmail);
	
	function shareEmail(){
		$("#emailModal").show();
	}
});
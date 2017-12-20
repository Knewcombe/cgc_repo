
(function($) {
	$(document).ready(function() {
		$("#dataTable").DataTable({
			 "aoColumnDefs" : [
				 {
				   'bSortable' : false,
				   'aTargets' : [ 'no-sort' ]
				 }]
		})
		
	});
})(jQuery);

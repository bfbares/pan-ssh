$(document).ready(function(){
	$('.upv').click(function(event){
		event.preventDefault();
		var id = $(this).data('id');
		var th = $(this);
		$.getJSON('/upvote_js?id='+id, function(data){
			$(th).closest('.vote').removeClass('alert-info').addClass('alert-success');
			$(th).closest('.vote').empty().html('<p><span class="badge badge-success">'+data.numVote+'</span></p><p>'+data.text+'</p>');
		});
	});
	$('.downv').click(function(event){
		event.preventDefault();
		var id = $(this).data('id');
		var th = $(this);
		$.getJSON('/downvote_js?id='+id, function(data){
			$(th).closest('.vote').removeClass('alert-info').addClass('alert-success');
			$(th).closest('.vote').empty().html('<p><span class="badge badge-success">'+data.numVote+'</span></p><p>'+data.text+'</p>');
		});
	});	
	$('.cupv').click(function(event){
		event.preventDefault();
		var id = $(this).data('id');
		var th = $(this);
		$.getJSON('/c_upvote_js?id='+id, function(data){
			$(th).closest('.pull-left').empty().html('<span class="badge badge-inverse">karma: '+data.karma+'</span>');
		});
	});
	$('.cdownv').click(function(event){
		event.preventDefault();
		var id = $(this).data('id');
		var th = $(this);
		$.getJSON('/c_downvote_js?id='+id, function(data){
			$(th).closest('.pull-left').empty().html('<span class="badge badge-inverse">karma: '+data.karma+'</span>');
		});
	});
});
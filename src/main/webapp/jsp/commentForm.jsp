<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="span4"></div>
	<div class="span4 well">
		<h1><s:text name="section.commentEdit"/></h1>
		<p></p>
		<s:actionerror theme="bootstrap"/>
		<s:form theme="bootstrap" namespace="/registered" action="comment_update">
	        <s:hidden name="id" value="%{comment.commentId}"/>
	        <div class="alert">
				<button class="close" data-dismiss="alert">×</button>
				<strong><s:text name="warning.warning" /></strong> <s:text name="warning.comment.length" />
			</div>
		    <s:textarea cssClass="input-xlarge" name="newComment" placeholder="getText('comment.comment')" value="%{comment.comment}" rows="4" tabindex="1"/>
		    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}" tabindex="2"/>
		</s:form>
	</div>
</div>
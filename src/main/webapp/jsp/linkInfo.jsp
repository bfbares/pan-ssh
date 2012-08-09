<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
   <div class="row-fluid well">
   	<s:if test="fullLink.getVoted()">
   		<div class="span1 pull-left alert alert-success vote">
   			<p><span class="badge badge-success"><s:property value="fullLink.numberOfVotes"/></span></p>
	    	<p><s:text name="form.voted" /></p>
    	</div>
   	</s:if>
   	<s:else>
    	<div class="span1 pull-left alert alert-info vote">
	    	<s:url action="upvote" id="upvote">
				<s:param name="id" value="fullLink.link.linkId" />
			</s:url>
	    	<p><s:a href="%{upvote}"><i class="icon-arrow-up"></i></s:a></p>
	    	<p><s:text name="form.vote" /></p>
	    	<p><span class="badge badge-info"><s:property value="fullLink.numberOfVotes"/></span></p>
	    	<s:url action="downvote" id="downvote">
				<s:param name="id" value="fullLink.link.linkId" />
			</s:url>
	    	<p><s:a href="%{downvote}"><i class="icon-arrow-down"></i></s:a></p>
    	</div>
   	</s:else>
   	<div class="span11 pull-right link">
   		<h2><a href="<s:url value="%{url}" />" target="_blank"><s:property value="fullLink.link.title"/></a></h2>
    	<h6><s:property value="fullLink.link.url"/></h6>
    	<p class="info"><s:text name="link.sendby" />: 
    	<s:if test="!fullLink.link.linkAuthor.level.toString().equals(\"DISABLED\") &&
    				!fullLink.link.linkAuthor.level.toString().equals(\"AUTODISABLED\")">
    		<s:a href="/user/%{fullLink.link.linkAuthor.login}"><s:property value="fullLink.link.linkAuthor.login"/></s:a>
    	</s:if>
    	<s:else>
    		<s:property value="fullLink.link.linkAuthor.login"/>
    	</s:else>
    	, <s:text name="link.sended" /> <s:date name="fullLink.link.submited"/>, <s:date name="fullLink.link.submited" nice="true"/></p>
    	<p class="description"><s:property value="fullLink.link.description"/></p>
    	<s:url action="reportForm" id="report">
			<s:param name="id" value="fullLink.link.linkId" />
		</s:url>
		<s:url action="link_edit" id="edit">
			<s:param name="id" value="fullLink.link.linkId" />
		</s:url>
    	<p class="category"><s:a href="/link/%{fullLink.link.ftitle}"><span class="badge badge-info"><s:property value="fullLink.numberOfVotes"/></span> <s:text name="link.coments" /></s:a> | <s:text name="link.category" />: <s:property value="fullLink.link.categoryId.name"/> | <s:text name="link.karma" />: <s:property value="fullLink.link.karma"/>
    	<s:if test="#session['user'] != null">
    		<s:if test="((#session['user'].level.toString() == \"ADMIN\") || 
             			(#session['user'].level.toString() == \"GOD\"))">
             	<s:if test="fullLink.link.status.toString() == \"DISCARD\" ||
             				fullLink.link.status.toString() == \"BANNED\"">
             		<s:url action="requeue" id="requeue">
						<s:param name="id" value="fullLink.link.linkId" />
					</s:url>
	    			<s:a href="%{requeue}"><span class="label label-success pull-right"><s:text name="link.requeue" /></span></s:a>
             	</s:if>
             	<s:if test="fullLink.link.status.toString() != \"BANNED\"">
	             	<s:url action="link_ban" id="ban">
						<s:param name="id" value="fullLink.link.linkId" />
					</s:url>
	    			<s:a href="%{ban}"><span class="label label-important pull-right"><s:text name="link.ban" /></span></s:a>
    			</s:if>
    		</s:if>
    		<s:if test="fullLink.getUserDisposable() ||
    					fullLink.getAdminDisposable() && 
    					((#session['user'].level.toString() == \"ADMIN\") || 
             				(#session['user'].level.toString() == \"GOD\"))">
             	<s:url action="link_discard" id="discard">
					<s:param name="id" value="fullLink.link.linkId" />
				</s:url>
    			<s:a href="%{discard}"><span class="label label-important pull-right"><s:text name="link.discard" /></span></s:a>
    		</s:if>
    		<s:url action="reportForm" id="report">
				<s:param name="id" value="fullLink.link.linkId" />
			</s:url>
    		<s:a href="%{report}"><span class="label label-warning pull-right"><s:text name="section.report" /></span></s:a>
    		<s:if test="fullLink.getEditable() ||
    					(#session['user'].level.toString() == \"ADMIN\") || 
             				(#session['user'].level.toString() == \"GOD\")">
             	<s:url action="link_edit" id="edit">
					<s:param name="id" value="fullLink.link.linkId" />
				</s:url>
    			<s:a href="%{edit}"><span class="label label-info pull-right"><s:text name="form.edit" /></span></s:a>
    		</s:if>
    	</s:if>	
    	</p>
   	</div>
   </div>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<p></p>
<s:if test="objectBlock.getList().isEmpty()">
	<p><s:text name="messages.isempty"/></p>
</s:if>
<s:else>
	<s:iterator value="objectBlock.list" var="fullLink">
	    <div class="row-fluid well">
	    	<s:if test="getVotable()">
		    	<s:if test="getVoted()">
		    		<div class="span1 pull-left alert alert-success vote">
		    			<p><span class="badge badge-success"><s:property value="numberOfVotes"/></span></p>
				    	<p><s:text name="form.voted" /></p>
			    	</div>
		    	</s:if>
		    	<s:else>
			    	<div class="span1 pull-left alert alert-info vote">
			    		<s:url action="upvote" id="upvote">
							<s:param name="id" value="link.linkId" />
						</s:url>
				    	<p><s:a href="%{upvote}"><i class="icon-arrow-up"></i></s:a></p>
				    	<p><s:text name="form.vote" /></p>
				    	<p><span class="badge badge-info"><s:property value="numberOfVotes"/></span></p>
				    	<s:url action="downvote" id="downvote">
							<s:param name="id" value="link.linkId" />
						</s:url>
				    	<p><s:a href="%{downvote}"><i class="icon-arrow-down"></i></s:a></p>
			    	</div>
		    	</s:else>
 		    </s:if>
 		    <s:else>
	    		<div class="span1 pull-left alert alert-error vote">
	    			<p><span class="badge badge-important"><s:property value="numberOfVotes"/></span></p>
			    	<p><s:text name="form.closed" /></p>
		    	</div>
 		    </s:else>
	    	<div class="span11 pull-right link">
	    		<h2><a href="<s:url value="%{url}" />" target="_blank"><s:property value="link.title"/></a></h2>
		    	<h6><s:property value="link.url"/></h6>
		    	<p class="info"><s:text name="link.sendby" />: 
		    	<s:if test="!#fullLink.link.linkAuthor.level.toString().equals(\"DISABLED\") &&
    						!#fullLink.link.linkAuthor.level.toString().equals(\"AUTODISABLED\")">
		    		<s:a href="/user/%{link.linkAuthor.login}"><s:property value="link.linkAuthor.login"/></s:a>
		    	</s:if>
		    	<s:else>
		    		<s:property value="link.linkAuthor.login"/>
		    	</s:else>
		    	, <s:text name="link.sended" /> <s:date name="link.submited"/>, <s:date name="link.submited" nice="true"/></p>
		    	<p class="description"><s:property value="link.description"/></p>
		    	<p class="category"><s:a href="/link/%{link.ftitle}"><span class="badge badge-info"><s:property value="numberOfVotes"/></span> <s:text name="link.coments" /></s:a> | <s:text name="link.category" />: <s:property value="link.categoryId.name"/> | <s:text name="link.karma" />: <s:property value="link.karma"/></p>
	    	</div>
	    </div>
	</s:iterator>
</s:else>
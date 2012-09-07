<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
   <div class="well disptable">
   	<s:if test="fullLink.getVotable()">
    	<s:if test="fullLink.getVoted()">
    		<%-- Link Already Voted --%>
    		<div class="span1 pull-left alert alert-success vote">
    			<p><span class="badge badge-success"><s:property value="fullLink.numberOfVotes"/></span></p>
		    	<p><s:text name="form.voted" /></p>
	    	</div>
    	</s:if>
    	<s:else>
    		<%-- Votable Link --%>
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
	    </s:if>
	    <s:else>
	    	<%-- Non votable link --%>
	   		<div class="span1 pull-left alert alert-error vote">
	   			<p><span class="badge badge-important"><s:property value="fullLink.numberOfVotes"/></span></p>
		    	<p><s:text name="form.closed" /></p>
	    	</div>
	    </s:else>
   	<div class="span11 pull-right link">
   		<%-- Title --%>
   		<h2><a href="<s:url value="%{fullLink.link.url}" />" target="_blank"><s:property value="fullLink.link.title"/></a></h2>
   		<%-- URL --%>
    	<h6><s:property value="fullLink.link.url"/></h6>
    	<%-- User and link info --%>
    	<p class="info"><s:text name="link.sendby" />: 
    	<s:if test="!fullLink.link.linkAuthor.level.toString().equals(\"DISABLED\") &&
    				!fullLink.link.linkAuthor.level.toString().equals(\"AUTODISABLED\")">
			<%-- User disabled --%>
    		<s:a href="/user/%{fullLink.link.linkAuthor.login}"><s:property value="fullLink.link.linkAuthor.login"/></s:a>
    	</s:if>
    	<s:else>
    		<s:property value="fullLink.link.linkAuthor.login"/>
    	</s:else>
    	, <s:text name="link.sended" /> <s:date name="fullLink.link.submited"/>, <s:date name="fullLink.link.submited" nice="true"/>
    	<s:if test="fullLink.link.status.toString().equals(\"PUBLISHED\")">
    		, <s:text name="link.published" /> <s:date name="fullLink.link.published"/>, <s:date name="fullLink.link.published" nice="true"/>
    	</s:if>
    	</p>
    	<p class="description"><s:property value="fullLink.link.description"/></p>
    	<%-- Comment number, category, karma --%>
    	<div class="category"><p><s:a href="/link/%{fullLink.link.ftitle}/"><span class="badge badge-info"><s:property value="fullLink.numberOfComments"/></span> <s:text name="link.coments" /></s:a> | 
    	<s:text name="link.category" />: <s:a href="/%{fullLink.link.categoryId.parent.toFriendly()}%{linkStatus}/"><s:property value="fullLink.link.categoryId.parent.name"/></s:a>, 
    	<s:a href="/%{fullLink.link.categoryId.toFriendly()}%{linkStatus}/"><s:property value="fullLink.link.categoryId.name"/></s:a> | <s:text name="link.karma" />: <s:property value="fullLink.link.karma"/></p>
    	<s:if test="#session['user'] != null">
    		<%-- More options --%>
	    	<div class="btn-group pull-right">
				<a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">
					<s:text name="section.moreoptions" />
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<%-- Report --%>
					<s:url action="reportForm" id="report">
						<s:param name="id" value="fullLink.link.linkId" />
					</s:url>
		    		<li><s:a href="%{report}"><s:text name="section.report" /></s:a></li>
					<s:if test="fullLink.getEditable() ||
    						(#session['user'].level.toString() == \"ADMIN\") || 
             				(#session['user'].level.toString() == \"GOD\")">
           				<%-- Edit --%>
		             	<s:url action="link_edit" id="edit">
							<s:param name="id" value="fullLink.link.linkId" />
						</s:url>
		    			<li><s:a href="%{edit}"><s:text name="form.edit" /></s:a></li>
		    		</s:if>	
		    		<s:if test="fullLink.getUserDisposable() ||
		    					fullLink.getAdminDisposable() && 
		    					((#session['user'].level.toString() == \"ADMIN\") || 
		             				(#session['user'].level.toString() == \"GOD\"))">
           				<%-- Discard --%>
		             	<s:url action="link_discard" id="discard">
							<s:param name="id" value="fullLink.link.linkId" />
						</s:url>
		    			<li><s:a href="%{discard}"><s:text name="link.discard" /></s:a></li>
		    		</s:if>
		    		<s:if test="((#session['user'].level.toString() == \"ADMIN\") || 
		             			(#session['user'].level.toString() == \"GOD\"))">
		             	<s:if test="fullLink.link.status.toString() == \"DISCARD\" ||
		             				fullLink.link.status.toString() == \"BANNED\"">
             				<%-- Requeue --%>
		             		<s:url action="requeue" id="requeue">
								<s:param name="id" value="fullLink.link.linkId" />
							</s:url>
			    			<li><s:a href="%{requeue}"><s:text name="link.requeue" /></s:a></li>
		             	</s:if>
		             	<s:if test="fullLink.link.status.toString() != \"BANNED\"">
		             		<%-- Ban --%>
			             	<s:url action="link_ban" id="ban">
								<s:param name="id" value="fullLink.link.linkId" />
							</s:url>
			    			<li><s:a href="%{ban}"><s:text name="link.ban" /></s:a></li>
		    			</s:if>
		    		</s:if>
				</ul>
			</div>  		
    	</s:if>	
    	</div>
   	</div>
   </div>
<%-- Comments --%>
<div class="comments">
	<h2><s:text name="section.comments" /></h2>
	<s:if test="objectBlock.getList().isEmpty()">
		<%-- No comments --%>
		<p><s:text name="comments.isempty"/></p>
	</s:if>
	<s:iterator value="objectBlock.list" var="c" status="status">
		<div class="comment">
			<s:if test="comment.status.toString() == \"ACTIVE\"">
				<div class="com-enabled">
					<p><span class="badge badge-success">#<s:property value="((page-1)*15)+#status.count"/></span> <s:property value="comment.comment"/></p>
			</s:if>
			<s:else>
				<div class="com-disabled">
					<p><strong><s:text name="comment.disabled" /></strong></p>
			</s:else>
				<s:if test="(mine && userEditable) ||
							(#session['user'].level.toString() == \"ADMIN\") ||
							(#session['user'].level.toString() == \"GOD\")">
					<s:if test="((#session['user'].level.toString() == \"ADMIN\" || 
								(#session['user'].level.toString() == \"GOD\")) &&
								comment.status.toString() == \"ACTIVE\")">
						<s:url action="comment_disable" id="comment_disable">
							<s:param name="id" value="comment.commentId" />
						</s:url>
						<s:a href="%{comment_disable}"><span class="label label-warning pull-right"><s:text name="comment.disable" /></span></s:a>
					</s:if>
					<s:if test="((#session['user'].level.toString() == \"ADMIN\" || 
								(#session['user'].level.toString() == \"GOD\")) &&
								comment.status.toString() == \"INACTIVE\")">
						<s:url action="comment_enable" id="comment_enable">
							<s:param name="id" value="comment.commentId" />
						</s:url>
						<s:a href="%{comment_enable}"><span class="label label pull-right"><s:text name="comment.enable" /></span></s:a>
					</s:if>
					<s:if test="(mine && userEditable) ||
								(!mine && adminEditable)">
						<s:url action="commentForm" id="c_edit">
							<s:param name="id" value="comment.commentId" />
						</s:url>
						<s:a href="%{c_edit}"><span class="label label-info pull-right"><s:text name="form.edit" /></span></s:a>
					</s:if>
				</s:if>
			</div>
			<div class="info">
				<s:if test="votable">
					<%-- Vote --%>
					<s:url action="c_upvote" id="c_upvote">
						<s:param name="id" value="comment.commentId" />
					</s:url>
			    	<p class="pull-left"><s:a href="%{c_upvote}"><i class="icon-arrow-up"></i></s:a>
			    	<span class="badge badge-inverse"><s:text name="comment.karma" />: <s:property value="comment.karma"/></span>
			    	<s:url action="c_downvote" id="c_downvote">
						<s:param name="id" value="comment.commentId" />
					</s:url>
			    	<s:a href="%{c_downvote}"><i class="icon-arrow-down"></i></s:a></p>
			    </s:if>
			    <s:else>
			    	<p class="pull-left"><span class="badge badge-inverse"><s:text name="comment.karma" />: <s:property value="comment.karma"/></span></p>
			    </s:else>
			    <%-- Date and user info --%>
				<p class="pull-right"><s:date name="comment.submited" nice="true"/> <s:text name="messages.by" /> 
				<s:if test="!comment.author.level.toString().equals(\"DISABLED\") &&
		    				!comment.author.level.toString().equals(\"AUTODISABLED\")">
		    		<s:a href="/user/%{comment.author.login}"><s:property value="comment.author.login"/></s:a>
		    	</s:if>
		    	<s:else>
		    		<s:property value="comment.author.login"/>
		    	</s:else>
		    	</p>
			</div>
		</div>
	</s:iterator>
</div>
<%-- New comment form --%>
<div class="row">
	<s:if test="#session['user'] != null">
		<div class="span3"></div>
		<div class="span7 well">
			<h2><s:text name="section.comment" /></h2>
			<s:form theme="bootstrap" namespace="/registered" action="comment_save">
		        <s:hidden name="linkId" value="%{fullLink.link.linkId}"/>
		        <div class="alert">
					<button class="close" data-dismiss="alert">×</button>
					<strong><s:text name="warning.warning" /></strong> <s:text name="warning.comment.length" />
				</div>
			    <s:textarea cssClass="input-xxlarge" name="comment.comment" placeholder="getText('comment.comment')" rows="4" tabindex="1"/>
			    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}" tabindex="2"/>
			</s:form>
		</div>
	</s:if>
</div>
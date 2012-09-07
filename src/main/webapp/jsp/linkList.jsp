<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<p></p>
<s:if test="objectBlock.getList().isEmpty()">
	<%-- No links --%>
	<p><s:text name="messages.isempty"/></p>
</s:if>
<s:else>
	<s:iterator value="objectBlock.list" var="fullLink">
	    <div class="well disptable">
	    	<s:if test="getVotable()">
		    	<s:if test="getVoted()">
		    		<%-- Link Already Voted --%>
		    		<div class="span1 pull-left alert alert-success vote">
		    			<p><span class="badge badge-success"><s:property value="numberOfVotes"/></span></p>
				    	<p><s:text name="form.voted" /></p>
			    	</div>
		    	</s:if>
		    	<s:else>
		    		<%-- Votable Link --%>
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
 		    	<%-- Non votable link --%>
	    		<div class="span1 pull-left alert alert-error vote">
	    			<p><span class="badge badge-important"><s:property value="numberOfVotes"/></span></p>
			    	<p><s:text name="form.closed" /></p>
		    	</div>
 		    </s:else>
	    	<div class="span11 pull-right link">
	    		<%-- Title --%>
	    		<h2><a href="<s:url value="%{link.url}" />" target="_blank"><s:property value="link.title"/></a></h2>
	    		<%-- URL --%>
		    	<h6><s:property value="link.url"/></h6>
		    	<%-- User and link info --%>
		    	<p class="info"><s:text name="link.sendby" />: 
		    	<s:if test="!#fullLink.link.linkAuthor.level.toString().equals(\"DISABLED\") &&
    						!#fullLink.link.linkAuthor.level.toString().equals(\"AUTODISABLED\")">
					<%-- User disabled --%>
		    		<s:a href="/user/%{link.linkAuthor.login}"><s:property value="link.linkAuthor.login"/></s:a>
		    	</s:if>
		    	<s:else>
		    		<s:property value="link.linkAuthor.login"/>
		    	</s:else>
		    	, <s:text name="link.sended" /> <s:date name="link.submited"/>, <s:date name="link.submited" nice="true"/>
		    	<s:if test="#fullLink.link.status.toString().equals(\"PUBLISHED\")">
		    		, <s:text name="link.published" /> <s:date name="link.published"/>, <s:date name="link.published" nice="true"/>
		    	</s:if>
		    	</p>
		    	<p class="description"><s:property value="link.description"/></p>
		    	<%-- Comment number, category, karma --%>
		    	<div class="category"><p><s:a href="/link/%{link.ftitle}/"><span class="badge badge-info"><s:property value="numberOfComments"/></span> <s:text name="link.coments" /></s:a> | 
		    	<s:text name="link.category" />: <s:a href="/%{link.categoryId.parent.toFriendly()}%{linkStatus}/"><s:property value="link.categoryId.parent.name"/></s:a>, 
		    	<s:a href="/%{link.categoryId.toFriendly()}%{linkStatus}/"><s:property value="link.categoryId.name"/></s:a> | <s:text name="link.karma" />: <s:property value="link.karma"/></p>
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
									<s:param name="id" value="link.linkId" />
								</s:url>
					    		<li><s:a href="%{report}"><s:text name="section.report" /></s:a></li>
								<s:if test="getEditable() ||
			    						(#session['user'].level.toString() == \"ADMIN\") || 
			             				(#session['user'].level.toString() == \"GOD\")">
		             				<%-- Edit --%>
					             	<s:url action="link_edit" id="edit">
										<s:param name="id" value="link.linkId" />
									</s:url>
					    			<li><s:a href="%{edit}"><s:text name="form.edit" /></s:a></li>
					    		</s:if>	
					    		<s:if test="getUserDisposable() ||
					    					getAdminDisposable() && 
					    					((#session['user'].level.toString() == \"ADMIN\") || 
					             				(#session['user'].level.toString() == \"GOD\"))">
		             				<%-- Discard --%>
					             	<s:url action="link_discard" id="discard">
										<s:param name="id" value="link.linkId" />
									</s:url>
					    			<li><s:a href="%{discard}"><s:text name="link.discard" /></s:a></li>
					    		</s:if>
					    		<s:if test="((#session['user'].level.toString() == \"ADMIN\") || 
					             			(#session['user'].level.toString() == \"GOD\"))">
					             	<s:if test="link.status.toString() == \"DISCARD\" ||
					             				link.status.toString() == \"BANNED\"">
			             				<%-- Requeue --%>
					             		<s:url action="requeue" id="requeue">
											<s:param name="id" value="link.linkId" />
										</s:url>
						    			<li><s:a href="%{requeue}"><s:text name="link.requeue" /></s:a></li>
					             	</s:if>
					             	<s:if test="link.status.toString() != \"BANNED\"">
					             		<%-- Ban --%>
						             	<s:url action="link_ban" id="ban">
											<s:param name="id" value="link.linkId" />
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
	</s:iterator>
</s:else>
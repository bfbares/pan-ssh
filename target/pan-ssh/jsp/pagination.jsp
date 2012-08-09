<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="!objectBlock.getList().isEmpty()">
	<div class="pagination pagination-centered">
	  	<ul>
	  		<s:if test="page == 1">
	  			<li class="disabled"><a>«</a></li>
	  			<li class="active"><a>1</a></li>
	  		</s:if>
	  		<s:else>
	  			<s:url id="url">
	            	<s:param name="page" value="%{page-1}" />
		        </s:url>
		  		<li><s:a href="%{actionurl}/%{page-1}">«</s:a></li>
		  		<s:url id="url">
	            	<s:param name="page" value="1" />
		        </s:url>
		  		<li><s:a href="%{actionurl}/1">1</s:a></li>
	  		</s:else>
	  		<s:if test="objectBlock.pagination.beginEllipsis">
	  			<li class="disabled"><a>...</a></li>
	  		</s:if>
			<s:iterator value="objectBlock.pagination.pageList" var="pageIt" status="pageListStatus">
				<s:url id="url">
		            <s:param name="page" value="#pageIt" />
		        </s:url>
		        <li 
		        	<s:if test="page == #pageIt">class="active" </s:if>
		        ><s:a href="%{actionurl}/%{#pageIt}"><s:property /></s:a></li>
			</s:iterator>
			<s:if test="objectBlock.pagination.endEllipsis">
	  			<li class="disabled"><a>...</a></li>
	  		</s:if>
			<s:if test="page == #attr['objectBlock.pagination.pageNum']">
				<s:if test="page != 1">
					<li class="active"><a><s:property value="page" /></a></li>
				</s:if>
	  			<li class="disabled"><a>»</a></li>
	  		</s:if>
	  		<s:else>
	  			<s:url id="url">
	            	<s:param name="page" value="%{objectBlock.pagination.pageNum}" />
		        </s:url>
		  		<li><s:a href="%{actionurl}/%{objectBlock.pagination.pageNum}"><s:property value="%{objectBlock.pagination.pageNum}" /></s:a></li>
	  			<s:url id="url">
	            	<s:param name="page" value="%{page+1}" />
		        </s:url>
		  		<li><s:a href="%{actionurl}/%{page+1}">»</s:a></li>
	  		</s:else>
		</ul>
	</div>
</s:if>
(this.webpackJsonpLamisPlus=this.webpackJsonpLamisPlus||[]).push([[0],{548:function(e,t,a){},549:function(e,t,a){},656:function(e,t,a){"use strict";a.r(t);var r=a(1),c=a.n(r),n=a(22),o=a.n(n),i=a(45),s=a(42),l=(a(146),a(548),a(549),a(550),a(9)),b=a(8),u=a(6),d=a(467),j=a(703),O=a(717),f=a(702),p=a(141),h=a(368),x=a(154),g=a.n(x),m=a(506),v=a.n(m),y=a(27),w=a(11),S=a(44),k=a(127),B=a.n(k),C=a(39),A=a.n(C),R=new URLSearchParams(window.location.search).get("jwt"),N="/api/v1/",P=a(5),z=function(e){var t=e.percentage;return Object(P.jsx)("div",{className:"progress",children:Object(P.jsxs)("div",{className:"progress-bar progress-bar-striped bg-success",role:"progressbar",style:{width:"".concat(t,"%"),height:"80px"},children:[t,"%"]})})},_=a(252),T=a.n(_),L=a(155),F=a.n(L),I=a(165),U=a.n(I),D=a(156),M=a.n(D),E=a(163),q=a.n(E),W=a(107),H=a.n(W),G=a(106),J=a.n(G),V=a(157),Y=a.n(V),K=a(158),Q=a.n(K),X=a(160),Z=a.n(X),$=a(161),ee=a.n($),te=a(162),ae=a.n(te),re=a(166),ce=a.n(re),ne=a(159),oe=a.n(ne),ie=a(164),se=a.n(ie),le=a(167),be=a.n(le),ue=a(468),de=(a(370),a(371),a(719)),je=a(704),Oe=a(705),fe=a(706),pe=a(707),he=a(708),xe=a(709),ge=a(710),me=a(711),ve=a(712),ye=a(713),we=a(205),Se=a.n(we),ke=a(321),Be=a.n(ke),Ce=a(204),Ae=a.n(Ce),Re=a(303),Ne=a.n(Re),Pe=(a(372),a(716)),ze=a(718),_e=a(715),Te={Add:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(F.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Check:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(M.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Clear:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Delete:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(Y.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),DetailPanel:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Edit:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(Q.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Export:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(oe.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Filter:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(Z.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),FirstPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(ee.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),LastPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(ae.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),NextPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),PreviousPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(q.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ResetSearch:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Search:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(se.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),SortArrow:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(U.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ThirdStateCheck:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(ce.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ViewColumn:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(be.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))}))},Le=Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}}})),Fe=function(e){var t=Le(),a=Object(r.useState)([]),c=Object(l.a)(a,2),n=c[0],o=c[1],i=Object(r.useState)([]),u=Object(l.a)(i,2),d=u[0],j=u[1],O=Object(r.useState)(!1),f=Object(l.a)(O,2),p=f[0],h=f[1],x=function(){return h(!p)},m=Object(r.useState)(!1),v=Object(l.a)(m,2),k=v[0],C=v[1],_=Object(r.useState)({facilityId:"",startDate:"",endDate:"",all:!0}),L=Object(l.a)(_,2),F=L[0],I=L[1],U=Object(r.useState)(!1),D=Object(l.a)(U,2),M=D[0],E=D[1],q=Object(r.useState)({}),W=Object(l.a)(q,2),H=W[0],G=W[1],J=Object(r.useState)(0),V=Object(l.a)(J,2),Y=V[0],K=V[1],Q=Object(r.useState)(!1),X=Object(l.a)(Q,2),Z=X[0],$=X[1],ee=Object(r.useState)([]),te=Object(l.a)(ee,2),ae=te[0],re=te[1],ce=Object(r.useState)(),ne=Object(l.a)(ce,2),oe=ne[0],ie=ne[1];Object(r.useEffect)((function(){!function(){we.apply(this,arguments)}(),le()}),[]);var se=function(){var e=Object(b.a)({},H);return e.facilityId=F.facilityId?"":"Facility is required",G(Object(b.a)({},e)),Object.values(e).every((function(e){return""===e}))};function le(){return be.apply(this,arguments)}function be(){return(be=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:A.a.get("".concat(N,"export/sync-histories"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){o(e.data)})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function we(){return(we=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:A.a.get("".concat(N,"account"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){j(Object.entries(e.data.applicationUserOrganisationUnits).map((function(e){var t=Object(l.a)(e,2),a=(t[0],t[1]);return{label:a.organisationUnitName,value:a.organisationUnitId}})))})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}var ke=function(){var e=Object(S.a)(Object(y.a)().mark((function e(t){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(t.preventDefault(),E(!0),!se()){e.next=18;break}return e.prev=3,e.next=6,A.a.get("".concat(N,"export/all?facilityId=").concat(F.facilityId,"&current=").concat(F.all),{headers:{Authorization:"Bearer ".concat(R)},onUploadProgress:function(e){K(parseInt(Math.round(100*e.loaded/e.total))),setTimeout((function(){return K(0)}),1e4)}});case 6:e.sent,s.b.success("JSON Extraction was successful!"),x(),le(),E(!1),e.next=16;break;case 13:e.prev=13,e.t0=e.catch(3),E(!1);case 16:e.next=19;break;case 18:s.b.error("Please select facility");case 19:case"end":return e.stop()}}),e,null,[[3,13]])})));return function(t){return e.apply(this,arguments)}}();return Object(P.jsxs)("div",{children:[!Z&&Object(P.jsxs)(P.Fragment,{children:[Object(P.jsx)(ue.a,{variant:"contained",style:{backgroundColor:"#014d88"},className:" float-right mr-1",onClick:function(){h(!p)},children:Object(P.jsx)("span",{style:{textTransform:"capitalize",color:"#fff"},children:"Generate JSON Files "})}),Object(P.jsx)("br",{}),Object(P.jsx)("br",{}),Object(P.jsx)(B.a,{icons:Te,title:"Generated JSON Files List ",columns:[{title:"Facility Name",field:"facilityName"},{title:"File Name ",field:"tableName",filtering:!1},{title:"Upload Size ",field:"uploadSize",filtering:!1},{title:"Date Generated ",field:"date",filtering:!1},{title:"Status",field:"status",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:n.map((function(e){return{facilityName:e.facilityName,tableName:e.tableName,uploadSize:e.uploadSize,date:g()(e.dateLastSync).format("LLLL"),status:null===e.errorLog?0===e.processed?"Processing":"Completed":"Error",actions:Object(P.jsx)("div",{children:Object(P.jsx)(Pe.a.Menu,{position:"right",children:Object(P.jsx)(Pe.a.Item,{children:Object(P.jsx)(ze.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(P.jsx)(_e.a,{item:!0,text:"Action",children:Object(P.jsx)(_e.a.Menu,{style:{marginTop:"10px"},children:null===e.errorLog?Object(P.jsxs)(P.Fragment,{children:[Object(P.jsxs)(_e.a.Item,{onClick:function(){return t=e.tableName,void A.a.get("".concat(N,"export/download/").concat(t),{headers:{Authorization:"Bearer ".concat(R)},responseType:"blob"}).then((function(e){var a=e.data,r=new Blob([a],{type:"application/octet-stream"});Ne.a.saveAs(r,"".concat(t))})).catch((function(e){}));var t},children:[Object(P.jsx)(Be.a,{color:"primary"})," Download File"]}),Object(P.jsxs)(_e.a.Item,{onClick:function(){return t=e.tableName,a=e.organisationUnitId,C(!0),console.log("the server call is here"),void A.a.post("".concat(N,"export/send-data?fileName=").concat(t,"&facilityId=").concat(a),t,{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){console.log("the server call is completed"),window.setTimeout((function(){s.b.success(" Uploading To server Successful!"),C(!1),le()}),1e3)})).catch((function(e){if(console.log("the server call error"),e.response&&e.response.data){var t=e.response.data.apierror&&""!==e.response.data.apierror.message?e.response.data.apierror.message:"Something went wrong, please try again";s.b.error(t),C(!1)}else C(!1),s.b.error("Something went wrong. Please try again...")}));var t,a},children:[Object(P.jsx)(T.a,{color:"primary"})," Send To Server"]})]}):Object(P.jsxs)(_e.a.Item,{onClick:function(){return function(e){$(!0),ie(e),re(e.errorLog)}(e)},children:[Object(P.jsx)(T.a,{color:"primary"}),"View Error"]})})})})})})})}})),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})]}),Z&&Object(P.jsxs)(P.Fragment,{children:[Object(P.jsx)(ue.a,{variant:"contained",style:{backgroundColor:"#014d88"},className:" float-right mr-1",onClick:function(){$(!1)},children:Object(P.jsxs)("span",{style:{textTransform:"capitalize",color:"#fff"},children:[" ","<<"," Back"]})}),Object(P.jsx)("br",{}),Object(P.jsx)("br",{}),Object(P.jsx)(B.a,{icons:Te,title:oe.facilityName,columns:[{title:"Name",field:"name",filtering:!1},{title:"Error",field:"error",filtering:!1},{title:"Others",field:"others",filtering:!1}],data:ae.map((function(e){return{name:e.name,error:e.error,others:e.others}})),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})]}),Object(P.jsx)(de.a,{isOpen:p,toggle:x,className:e.className,size:"lg",backdrop:"static",children:Object(P.jsxs)(je.a,{children:[Object(P.jsx)(Oe.a,{toggle:x,children:"Generate JSON Files"}),Object(P.jsx)(fe.a,{children:Object(P.jsx)(pe.a,{children:Object(P.jsxs)(he.a,{children:[Object(P.jsxs)(xe.a,{children:[Object(P.jsx)(ge.a,{md:12,children:Object(P.jsxs)(me.a,{children:[Object(P.jsx)(ve.a,{children:"Facility *"}),Object(P.jsxs)(ye.a,{type:"select",name:"facilityId",id:"facilityId",onChange:function(e){I(Object(b.a)(Object(b.a)({},F),{},Object(w.a)({},e.target.name,e.target.value)))},style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:F.facilityId,children:[Object(P.jsx)("option",{children:" "}),d.map((function(e){var t=e.label,a=e.value;return Object(P.jsx)("option",{value:a,children:t},a)}))]}),""!==H.facilityId?Object(P.jsx)("span",{className:t.error,children:H.facilityId}):""]})}),Object(P.jsxs)("div",{className:"form-check custom-checkbox ml-3 ",children:[Object(P.jsx)("input",{type:"checkbox",className:"form-check-input",name:"all",id:"all",onChange:function(e){e.target.checked?I(Object(b.a)(Object(b.a)({},F),{},Object(w.a)({},"all",e.target.checked))):I(Object(b.a)(Object(b.a)({},F),{},Object(w.a)({},"all",!1)))},checked:F.all}),Object(P.jsx)("label",{className:"form-check-label",htmlFor:"all",children:"Recent Update ?"})]})]}),Object(P.jsx)("br",{}),Object(P.jsx)("b",{children:!0===F.all?"Only the updated records will be pushed":"You are pushing record from initial"}),Object(P.jsx)("br",{}),M?Object(P.jsx)(z,{percentage:Y}):"",Object(P.jsx)("br",{}),Object(P.jsx)(ue.a,{type:"submit",variant:"contained",color:"primary",className:t.button,style:{backgroundColor:"#014d88",fontWeight:"bolder"},startIcon:Object(P.jsx)(Ae.a,{}),onClick:ke,children:M?Object(P.jsx)("span",{style:{textTransform:"capitalize"},children:"Generating Please Wait..."}):Object(P.jsx)("span",{style:{textTransform:"capitalize"},children:"Generate"})}),Object(P.jsx)(ue.a,{variant:"contained",color:"default",onClick:x,className:t.button,style:{backgroundColor:"#992E62"},startIcon:Object(P.jsx)(Se.a,{}),children:Object(P.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"cancel"})})]})})})]})}),Object(P.jsx)(de.a,{isOpen:k,toggle:function(){return C(!k)},backdrop:!1,fade:!0,style:{marginTop:"250px"},size:"lg",children:Object(P.jsx)(fe.a,{children:Object(P.jsx)("h1",{children:"Uploading File To Server. Please wait..."})})})]})},Ie=a(714),Ue=Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}}})),De=function(e){var t,a=Ue(),c=Object(r.useState)(!1),n=Object(l.a)(c,2),o=(n[0],n[1],Object(r.useState)({username:"",password:"",url:""})),i=Object(l.a)(o,2),u=i[0],d=i[1],j=Object(r.useState)(!1),O=Object(l.a)(j,2),f=O[0],p=O[1],h=Object(r.useState)([]),x=Object(l.a)(h,2),g=(x[0],x[1]),m=Object(r.useState)({}),v=Object(l.a)(m,2),k=v[0],B=v[1];function C(){return(C=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:A.a.get("".concat(N,"sync/remote-urls"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){g(Object.entries(e.data).map((function(e){var t=Object(l.a)(e,2),a=(t[0],t[1]);return{label:a.url,value:a.id}})))})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Object(r.useEffect)((function(){!function(){C.apply(this,arguments)}()}),[]);var z=function(e){d(Object(b.a)(Object(b.a)({},u),{},Object(w.a)({},e.target.name,e.target.value)))};return Object(P.jsx)("div",{children:Object(P.jsx)(de.a,(t={isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:!1},Object(w.a)(t,"backdrop","static"),Object(w.a)(t,"children",Object(P.jsxs)(je.a,{children:[Object(P.jsx)(Oe.a,{toggle:e.toggleModal,children:"Personal Access Token "}),Object(P.jsx)(fe.a,{children:Object(P.jsx)(pe.a,{children:Object(P.jsxs)(he.a,{children:[Object(P.jsxs)(xe.a,{children:[Object(P.jsx)(ge.a,{md:12,children:Object(P.jsxs)(me.a,{children:[Object(P.jsx)(ve.a,{children:"Server URL * "}),Object(P.jsx)(ye.a,{type:"text",name:"url",id:"url",value:u.url,onChange:z,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==k.url?Object(P.jsx)("span",{className:a.error,children:k.url}):""]})}),Object(P.jsx)(ge.a,{md:12,children:Object(P.jsxs)(me.a,{children:[Object(P.jsx)(ve.a,{children:"Username "}),Object(P.jsx)(ye.a,{type:"text",name:"username",id:"username",value:u.username,onChange:z,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==k.username?Object(P.jsx)("span",{className:a.error,children:k.username}):""]})}),Object(P.jsx)(ge.a,{md:12,children:Object(P.jsxs)(me.a,{children:[Object(P.jsx)(ve.a,{children:"Password "}),Object(P.jsx)(ye.a,{type:"password",name:"password",id:"password",value:u.password,onChange:z,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==k.password?Object(P.jsx)("span",{className:a.error,children:k.password}):""]})})]}),f?Object(P.jsx)(Ie.a,{}):"",Object(P.jsx)("br",{}),Object(P.jsx)(ue.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:function(t){t.preventDefault(),function(){var e=Object(b.a)({},k);return e.username=u.username?"":"Username is required",e.password=u.password?"":"Password is required",e.url=u.url?"":"Server URL is required",B(Object(b.a)({},e)),Object.values(e).every((function(e){return""===e}))}()&&(p(!0),A.a.post("".concat(N,"sync/remote-access-token"),u,{headers:{Authorization:"Bearer ".concat(R)}}).then((function(t){p(!1),e.ServerUrl(),s.b.success("Token Generated Successful"),e.toggleModal()})).catch((function(t){p(!1),s.b.error("Something went wrong"),e.toggleModal()})))},children:Object(P.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Connect & Generate Token"})})]})})})]})),t))})},Me=Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}}})),Ee=function(e){var t,a=Me(),c=Object(r.useState)({username:e.userToken.username,password:"",url:e.userToken.url}),n=Object(l.a)(c,2),o=n[0],i=n[1],u=Object(r.useState)(!1),d=Object(l.a)(u,2),j=d[0],O=d[1],f=Object(r.useState)([]),p=Object(l.a)(f,2),h=(p[0],p[1]),x=Object(r.useState)({}),g=Object(l.a)(x,2),m=g[0],v=g[1];function k(){return(k=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:A.a.get("".concat(N,"sync/remote-urls"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){h(Object.entries(e.data).map((function(e){var t=Object(l.a)(e,2),a=(t[0],t[1]);return{label:a.url,value:a.id}})))})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Object(r.useEffect)((function(){!function(){k.apply(this,arguments)}()}),[]);var B=function(e){i(Object(b.a)(Object(b.a)({},o),{},Object(w.a)({},e.target.name,e.target.value)))};return Object(P.jsx)("div",{children:Object(P.jsx)(de.a,(t={isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:!1},Object(w.a)(t,"backdrop","static"),Object(w.a)(t,"children",Object(P.jsxs)(je.a,{children:[Object(P.jsx)(Oe.a,{toggle:e.toggleModal,children:"Update Personal Access Token "}),Object(P.jsx)(fe.a,{children:Object(P.jsx)(pe.a,{children:Object(P.jsxs)(he.a,{children:[Object(P.jsxs)(xe.a,{children:[Object(P.jsx)(ge.a,{md:12,children:Object(P.jsxs)(me.a,{children:[Object(P.jsx)(ve.a,{children:"Server URL* "}),Object(P.jsx)(ye.a,{type:"text",name:"url",id:"url",value:o.url,onChange:B,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==m.url?Object(P.jsx)("span",{className:a.error,children:m.url}):""]})}),Object(P.jsx)(ge.a,{md:12,children:Object(P.jsxs)(me.a,{children:[Object(P.jsx)(ve.a,{children:"Username "}),Object(P.jsx)(ye.a,{type:"text",name:"username",id:"username",value:o.username,onChange:B,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==m.username?Object(P.jsx)("span",{className:a.error,children:m.username}):""]})}),Object(P.jsx)(ge.a,{md:12,children:Object(P.jsxs)(me.a,{children:[Object(P.jsx)(ve.a,{children:"Password "}),Object(P.jsx)(ye.a,{type:"password",name:"password",id:"password",value:o.password,onChange:B,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==m.password?Object(P.jsx)("span",{className:a.error,children:m.password}):""]})})]}),j?Object(P.jsx)(Ie.a,{}):"",Object(P.jsx)("br",{}),Object(P.jsx)(ue.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:function(t){t.preventDefault(),function(){var e=Object(b.a)({},m);return e.username=o.username?"":"Username is required",e.password=o.password?"":"Password is required",e.url=o.url?"":"Server URL is required",v(Object(b.a)({},e)),Object.values(e).every((function(e){return""===e}))}()&&(O(!0),A.a.put("".concat(N,"sync/remote-access-token/").concat(e.userToken.id),o,{headers:{Authorization:"Bearer ".concat(R)}}).then((function(t){O(!1),e.ServerUrl(),s.b.success("Token Generated Successful"),e.toggleModal()})).catch((function(t){O(!1),s.b.error("Something went wrong"),e.toggleModal()})))},children:Object(P.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Connect & Update Token"})})]})})})]})),t))})},qe=a(512),We={Add:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(F.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Check:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(M.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Clear:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Delete:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(Y.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),DetailPanel:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Edit:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(Q.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Export:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(oe.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Filter:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(Z.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),FirstPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(ee.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),LastPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(ae.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),NextPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),PreviousPage:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(q.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ResetSearch:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Search:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(se.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),SortArrow:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(U.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ThirdStateCheck:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(ce.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ViewColumn:Object(r.forwardRef)((function(e,t){return Object(P.jsx)(be.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))}))},He=function(e){var t=Object(r.useState)([]),a=Object(l.a)(t,2),n=a[0],o=a[1],i=Object(r.useState)(!1),s=Object(l.a)(i,2),b=(s[0],s[1],c.a.useState(!1)),u=Object(l.a)(b,2),d=u[0],j=u[1],O=c.a.useState(!1),f=Object(l.a)(O,2),p=f[0],h=f[1],x=Object(r.useState)({username:"",password:"",url:""}),g=Object(l.a)(x,2),m=g[0],v=g[1];function w(){return k.apply(this,arguments)}function k(){return(k=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:A.a.get("".concat(N,"sync/remote-urls"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){o(e.data)})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Object(r.useEffect)((function(){w()}),[]);return Object(P.jsxs)("div",{children:[Object(P.jsx)(ue.a,{variant:"contained",color:"primary",className:" float-right mr-1",style:{backgroundColor:"#014d88"},onClick:function(){j(!d)},children:Object(P.jsx)("span",{style:{textTransform:"capitalize"},children:"New Personal Access Token "})}),Object(P.jsx)("br",{}),Object(P.jsx)("br",{}),Object(P.jsx)("br",{}),Object(P.jsx)(B.a,{icons:We,title:"Personal Access Token List",columns:[{title:"URLS",field:"name"},{title:"Username",field:"url",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:n.map((function(e){return{name:e.url,url:e.username,actions:Object(P.jsx)("div",{children:Object(P.jsx)(Pe.a.Menu,{position:"right",children:Object(P.jsx)(Pe.a.Item,{children:Object(P.jsx)(ze.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(P.jsx)(_e.a,{item:!0,text:"Action",children:Object(P.jsx)(_e.a.Menu,{style:{marginTop:"10px"},children:Object(P.jsxs)(_e.a.Item,{onClick:function(){return function(e){h(!p),v(e)}(e)},children:[Object(P.jsx)(qe.a,{}),"Edit Token"]})})})})})})})}})),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!1,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}}),Object(P.jsx)(De,{toggleModal:function(){return j(!d)},showModal:d,ServerUrl:w}),Object(P.jsx)(Ee,{toggleModal:function(){return h(!p)},showModal:p,ServerUrl:w,userToken:m})]})},Ge=function(e){var t=Object(r.useState)({username:"",password:"",url:""}),a=Object(l.a)(t,2),c=(a[0],a[1],Object(r.useState)(!1)),n=Object(l.a)(c,2),o=(n[0],n[1],Object(r.useState)([])),i=Object(l.a)(o,2),s=(i[0],i[1]);function b(){return(b=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:A.a.get("".concat(N,"sync/remote-urls")).then((function(e){console.log(e.data),s(Object.entries(e.data).map((function(e){var t=Object(l.a)(e,2),a=(t[0],t[1]);return{label:a.url,value:a.id}})))})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Object(r.useEffect)((function(){!function(){b.apply(this,arguments)}()}),[]);return Object(P.jsx)(He,{})},Je=a(515),Ve=a.n(Je),Ye=a(514),Ke=a.n(Ye);a(143),Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}}})),a(513),a(518),Object(d.a)((function(e){return{root:{width:"100%",maxWidth:360,backgroundColor:e.palette.background.paper,"& > * + *":{marginTop:e.spacing(2)}}}}));var Qe=["children","value","index"];g.a.locale("en"),v()();var Xe=Object(d.a)((function(e){return{header:{fontSize:"20px",fontWeight:"bold",padding:"5px",paddingBottom:"10px"},inforoot:{margin:"5px"},dropdown:{marginTop:"50px"},paper:{marginRight:e.spacing(2)},downmenu:{display:"flex"}}}));function Ze(e){var t=e.children,a=e.value,r=e.index,c=Object(u.a)(e,Qe);return Object(P.jsx)(p.a,Object(b.a)(Object(b.a)({component:"div",role:"tabpanel",hidden:a!==r,id:"scrollable-force-tabpanel-".concat(r),"aria-labelledby":"scrollable-force-tab-".concat(r)},c),{},{children:a===r&&Object(P.jsx)(h.a,{p:5,children:t})}))}function $e(e){return{id:"scrollable-force-tab-".concat(e),"aria-controls":"scrollable-force-tabpanel-".concat(e)}}var et=function(e){var t=Xe(),a=Object(r.useState)(null),c=Object(l.a)(a,2),n=c[0],o=c[1],i=e.location&&e.location.state?e.location.state:" ",s=function(e,t){var a=t,r=new RegExp("[?&]"+e+"=([^&#]*)","i").exec(a);return r?r[1]:null}("tab",e.location&&e.location.search?e.location.search:""),u=null!==s?s:i;return Object(r.useEffect)((function(){switch(u){case"database-sync":default:return o(0);case"setting":return o(1)}}),[s]),Object(P.jsxs)(P.Fragment,{children:[Object(P.jsx)("div",{className:"row page-titles mx-0",style:{marginTop:"0px",marginBottom:"-10px"},children:Object(P.jsx)("ol",{className:"breadcrumb",children:Object(P.jsx)("li",{className:"breadcrumb-item active",children:Object(P.jsx)("h4",{children:"Central Sync"})})})}),Object(P.jsx)("br",{}),Object(P.jsxs)("div",{className:t.root,children:[Object(P.jsx)(j.a,{position:"static",style:{backgroundColor:"#fff"},children:Object(P.jsxs)(O.a,{value:n,onChange:function(e,t){o(t)},variant:"scrollable",scrollButtons:"on",indicatorColor:"secondary",textColor:"primary","aria-label":"scrollable force tabs example",children:[Object(P.jsx)(f.a,Object(b.a)({className:t.title,label:"Generate & Upload JSON Files",icon:Object(P.jsx)(Ke.a,{})},$e(0))),Object(P.jsx)(f.a,Object(b.a)({className:t.title,label:"Configuration  ",icon:Object(P.jsx)(Ve.a,{})},$e(1)))]})}),Object(P.jsx)(Ze,{value:n,index:0,children:Object(P.jsx)(Fe,{})}),Object(P.jsx)(Ze,{value:n,index:1,children:Object(P.jsx)(Ge,{})})]})]})};function tt(){return Object(P.jsx)(i.a,{children:Object(P.jsxs)("div",{children:[Object(P.jsx)(s.a,{}),Object(P.jsx)(i.d,{children:Object(P.jsx)(i.b,{path:"/",children:Object(P.jsx)(et,{})})})]})})}var at=a(286),rt=function(e){e&&e instanceof Function&&a.e(6).then(a.bind(null,892)).then((function(t){var a=t.getCLS,r=t.getFID,c=t.getFCP,n=t.getLCP,o=t.getTTFB;a(e),r(e),c(e),n(e),o(e)}))},ct=a(516),nt="ltr",ot=[{typography:"poppins",version:"light",layout:"vertical",headerBg:"color_1",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"full",direction:nt},{typography:"poppins",version:"light",layout:"vertical",primary:"color_5",headerBg:"color_5",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",direction:nt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_11",headerBg:"color_1",sidebarBg:"color_11",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_11",direction:nt},{typography:"poppins",version:"dark",layout:"vertical",headerBg:"color_3",navheaderBg:"color_3",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_1",direction:nt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_15",headerBg:"color_1",sidebarStyle:"full",sidebarBg:"color_1",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_15",direction:nt},{typography:"poppins",version:"light",layout:"horizontal",navheaderBg:"color_1",headerBg:"color_1",sidebarBg:"color_9",sidebarStyle:"modern",sidebarPosition:"static",headerPosition:"fixed",containerLayout:"wide",primary:"color_9",direction:nt}],it=Object(r.createContext)(),st=function(e){var t=Object(r.useState)({value:"full",label:"Full"}),a=Object(l.a)(t,2),c=a[0],n=a[1],o=Object(r.useState)({value:"fixed",label:"Fixed"}),i=Object(l.a)(o,2),s=i[0],b=i[1],u=Object(r.useState)({value:"fixed",label:"Fixed"}),d=Object(l.a)(u,2),j=d[0],O=d[1],f=Object(r.useState)({value:"vertical",label:"Vertical"}),p=Object(l.a)(f,2),h=p[0],x=p[1],g=Object(r.useState)({value:"ltr",label:"LTR"}),m=Object(l.a)(g,2),v=m[0],y=m[1],w=Object(r.useState)("color_1"),S=Object(l.a)(w,2),k=S[0],B=S[1],C=Object(r.useState)("color_1"),A=Object(l.a)(C,2),R=A[0],N=A[1],z=Object(r.useState)("color_1"),_=Object(l.a)(z,2),T=_[0],L=_[1],F=Object(r.useState)("color_1"),I=Object(l.a)(F,2),U=I[0],D=I[1],M=Object(r.useState)(!1),E=Object(l.a)(M,2),q=E[0],W=E[1],H=Object(r.useState)(!1),G=Object(l.a)(H,2),J=G[0],V=G[1],Y=Object(r.useState)({value:"light",label:"Light"}),K=Object(l.a)(Y,2),Q=K[0],X=K[1],Z=Object(r.useState)({value:"wide-boxed",label:"Wide Boxed"}),$=Object(l.a)(Z,2),ee=$[0],te=$[1],ae=document.querySelector("body"),re=Object(r.useState)(0),ce=Object(l.a)(re,2),ne=ce[0],oe=ce[1],ie=Object(r.useState)(0),se=Object(l.a)(ie,2),le=se[0],be=se[1],ue=function(e){B(e),ae.setAttribute("data-primary",e)},de=function(e){N(e),ae.setAttribute("data-nav-headerbg",e)},je=function(e){L(e),ae.setAttribute("data-headerbg",e)},Oe=function(e){D(e),ae.setAttribute("data-sibebarbg",e)},fe=function(e){b(e),ae.setAttribute("data-sidebar-position",e.value)},pe=function(e){y(e),ae.setAttribute("direction",e.value);var t=document.querySelector("html");t.setAttribute("dir",e.value),t.className=e.value},he=function(e){"horizontal"===e.value&&"overlay"===c.value?(x(e),ae.setAttribute("data-layout",e.value),n({value:"full",label:"Full"}),ae.setAttribute("data-sidebar-style","full")):(x(e),ae.setAttribute("data-layout",e.value))},xe=function(e){"horizontal"===h.value&&"overlay"===e.value?alert("Sorry! Overlay is not possible in Horizontal layout."):(n(e),W("icon-hover"===e.value?"_i-hover":""),ae.setAttribute("data-sidebar-style",e.value))},ge=function(e){O(e),ae.setAttribute("data-header-position",e.value)},me=function(e){ae.setAttribute("data-theme-version",e.value),X(e)},ve=function(e){te(e),ae.setAttribute("data-container",e.value),"boxed"===e.value&&xe({value:"overlay",label:"Overlay"})};return Object(r.useEffect)((function(){var e=document.querySelector("body");e.setAttribute("data-typography","poppins"),e.setAttribute("data-theme-version","light"),e.setAttribute("data-layout","vertical"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-nav-headerbg","color_1"),e.setAttribute("data-headerbg","color_1"),e.setAttribute("data-sidebar-style","overlay"),e.setAttribute("data-sibebarbg","color_1"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-sidebar-position","fixed"),e.setAttribute("data-header-position","fixed"),e.setAttribute("data-container","wide"),e.setAttribute("direction","ltr");var t=function(){oe(window.innerWidth),be(window.innerHeight),window.innerWidth>=768&&window.innerWidth<1024?e.setAttribute("data-sidebar-style","mini"):window.innerWidth<=768?e.setAttribute("data-sidebar-style","overlay"):e.setAttribute("data-sidebar-style","full")};return t(),window.addEventListener("resize",t),function(){return window.removeEventListener("resize",t)}}),[]),Object(P.jsx)(it.Provider,{value:{body:ae,sideBarOption:[{value:"compact",label:"Compact"},{value:"full",label:"Full"},{value:"mini",label:"Mini"},{value:"modern",label:"Modern"},{value:"overlay",label:"Overlay"},{value:"icon-hover",label:"Icon-hover"}],layoutOption:[{value:"vertical",label:"Vertical"},{value:"horizontal",label:"Horizontal"}],backgroundOption:[{value:"light",label:"Light"},{value:"dark",label:"Dark"}],sidebarposition:s,headerPositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],containerPosition:[{value:"wide-boxed",label:"Wide Boxed"},{value:"boxed",label:"Boxed"},{value:"wide",label:"Wide"}],directionPosition:[{value:"ltr",label:"LTR"},{value:"rtl",label:"RTL"}],fontFamily:[{value:"poppins",label:"Poppins"},{value:"roboto",label:"Roboto"},{value:"cairo",label:"Cairo"},{value:"opensans",label:"Open Sans"},{value:"HelveticaNeue",label:"HelveticaNeue"}],primaryColor:k,navigationHader:R,windowWidth:ne,windowHeight:le,changePrimaryColor:ue,changeNavigationHader:de,changeSideBarStyle:xe,sideBarStyle:c,changeSideBarPostion:fe,sidebarpositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],changeHeaderPostion:ge,headerposition:j,changeSideBarLayout:he,sidebarLayout:h,changeDirectionLayout:pe,changeContainerPosition:ve,direction:v,colors:["color_1","color_2","color_3","color_4","color_5","color_6","color_7","color_8","color_9","color_10","color_11","color_12","color_13","color_14","color_15"],haderColor:T,chnageHaderColor:je,chnageSidebarColor:Oe,sidebarColor:U,iconHover:q,menuToggle:J,openMenuToggle:function(){"overly"===c.value?V(!0):V(!1)},changeBackground:me,background:Q,containerPosition_:ee,setDemoTheme:function(e,t){var a={},r=ot[e];ae.setAttribute("data-typography",r.typography),a.value=r.version,me(a),a.value=r.layout,he(a),ue(r.primary),de(r.navheaderBg),je(r.headerBg),a.value=r.sidebarStyle,xe(a),Oe(r.sidebarBg),a.value=r.sidebarPosition,fe(a),a.value=r.headerPosition,ge(a),a.value=r.containerLayout,ve(a),a.value=t,pe(a)}},children:e.children})};o.a.render(Object(P.jsx)(c.a.StrictMode,{children:Object(P.jsx)(ct.a,{children:Object(P.jsx)(at.a,{basename:"/",children:Object(P.jsx)(st,{children:Object(P.jsx)(tt,{})})})})}),document.getElementById("root")),rt()}},[[656,1,2]]]);
//# sourceMappingURL=main.b42cbb69.chunk.js.map
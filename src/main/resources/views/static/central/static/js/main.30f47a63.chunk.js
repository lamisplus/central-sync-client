(this.webpackJsonpLamisPlus=this.webpackJsonpLamisPlus||[]).push([[0],{548:function(e,t,a){},549:function(e,t,a){},656:function(e,t,a){"use strict";a.r(t);var r=a(0),c=a.n(r),n=a(19),i=a.n(n),o=a(43),s=a(42),l=(a(177),a(548),a(549),a(550),a(8)),b=a(7),u=a(5),d=a(465),j=a(705),f=a(719),O=a(704),p=a(139),h=a(368),g=a(152),x=a.n(g),m=a(506),v=a.n(m),y=a(29),w=a(10),S=a(45),B=a(153),A=a.n(B),P=a(39),k=a.n(P),R=new URLSearchParams(window.location.search).get("jwt"),C="/api/v1/",_=a(4),N=function(e){var t=e.percentage;return Object(_.jsx)("div",{className:"progress",children:Object(_.jsxs)("div",{className:"progress-bar progress-bar-striped bg-success",role:"progressbar",style:{width:"".concat(t,"%"),height:"80px"},children:[t,"%"]})})},z=a(321),L=a.n(z),T=a(154),F=a.n(T),I=a(164),D=a.n(I),U=a(155),E=a.n(U),M=a(162),W=a.n(M),q=a(106),H=a.n(q),G=a(105),J=a.n(G),V=a(156),K=a.n(V),Q=a(157),X=a.n(Q),Y=a(159),Z=a.n(Y),$=a(160),ee=a.n($),te=a(161),ae=a.n(te),re=a(165),ce=a.n(re),ne=a(158),ie=a.n(ne),oe=a(163),se=a.n(oe),le=a(166),be=a.n(le),ue=a(466),de=(a(370),a(371),a(721)),je=a(706),fe=a(707),Oe=a(708),pe=a(709),he=a(710),ge=a(711),xe=a(712),me=a(713),ve=a(714),ye=a(715),we=a(206),Se=a.n(we),Be=a(320),Ae=a.n(Be),Pe=a(205),ke=a.n(Pe),Re=a(302),Ce=a.n(Re),_e=(a(485),a(718)),Ne=a(720),ze=a(717),Le={Add:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(F.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Check:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(E.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Clear:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Delete:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(K.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),DetailPanel:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Edit:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(X.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Export:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ie.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Filter:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(Z.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),FirstPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ee.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),LastPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ae.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),NextPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),PreviousPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(W.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ResetSearch:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Search:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(se.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),SortArrow:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(D.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ThirdStateCheck:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ce.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ViewColumn:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(be.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))}))},Te=Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}}})),Fe=function(e){var t=Te(),a=Object(r.useState)([]),c=Object(l.a)(a,2),n=c[0],i=c[1],o=Object(r.useState)([]),u=Object(l.a)(o,2),d=u[0],j=u[1],f=Object(r.useState)(!1),O=Object(l.a)(f,2),p=O[0],h=O[1],g=function(){return h(!p)},m=Object(r.useState)(!1),v=Object(l.a)(m,2),B=v[0],P=v[1],z=Object(r.useState)({facilityId:"",startDate:"",endDate:""}),T=Object(l.a)(z,2),F=T[0],I=T[1],D=Object(r.useState)(!1),U=Object(l.a)(D,2),E=U[0],M=U[1],W=Object(r.useState)({}),q=Object(l.a)(W,2),H=q[0],G=q[1],J=Object(r.useState)(0),V=Object(l.a)(J,2),K=V[0],Q=V[1];Object(r.useEffect)((function(){!function(){Y.apply(this,arguments)}(),function(){ee.apply(this,arguments)}(),Z()}),[]);var X=function(){var e=Object(b.a)({},H);return e.facilityId=F.facilityId?"":"Facility is required",G(Object(b.a)({},e)),Object.values(e).every((function(e){return""===e}))};function Y(){return(Y=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:k.a.get("".concat(C,"account"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){i(e.data.applicationUserOrganisationUnits)})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function Z(){return $.apply(this,arguments)}function $(){return($=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:k.a.get("".concat(C,"export/sync-histories"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){i(e.data)})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function ee(){return(ee=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:k.a.get("".concat(C,"account"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){j(Object.entries(e.data.applicationUserOrganisationUnits).map((function(e){var t=Object(l.a)(e,2),a=(t[0],t[1]);return{label:a.organisationUnitName,value:a.organisationUnitId}})))})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}var te=function(){var e=Object(S.a)(Object(y.a)().mark((function e(t){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(t.preventDefault(),M(!0),!X()){e.next=16;break}return e.prev=3,e.next=6,k.a.get("".concat(C,"export/all?facilityId=").concat(F.facilityId),{headers:{Authorization:"Bearer ".concat(R)},onUploadProgress:function(e){Q(parseInt(Math.round(100*e.loaded/e.total))),setTimeout((function(){return Q(0)}),1e4)}});case 6:e.sent,s.b.success("JSON Extraction was successful!"),g(),Z(),e.next=14;break;case 12:e.prev=12,e.t0=e.catch(3);case 14:e.next=17;break;case 16:s.b.error("All Fields are required");case 17:case"end":return e.stop()}}),e,null,[[3,12]])})));return function(t){return e.apply(this,arguments)}}();return Object(_.jsxs)("div",{children:[Object(_.jsx)(ue.a,{variant:"contained",style:{backgroundColor:"#014d88"},className:" float-right mr-1",onClick:function(){h(!p)},children:Object(_.jsx)("span",{style:{textTransform:"capitalize",color:"#fff"},children:"Generate JSON Files "})}),Object(_.jsx)("br",{}),Object(_.jsx)("br",{}),Object(_.jsx)(A.a,{icons:Le,title:"Generated JSON Files List ",columns:[{title:"Facility Name",field:"facilityName"},{title:"File Name ",field:"tableName",filtering:!1},{title:"Upload Size ",field:"uploadSize",filtering:!1},{title:"Date Generated ",field:"date",filtering:!1},{title:"Status",field:"status",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:n.map((function(e){return{facilityName:e.facilityName,tableName:e.tableName,uploadSize:e.uploadSize,date:x()(e.dateLastSync).format("LLLL"),status:0===e.processed?"Processing":"Completed",actions:Object(_.jsx)("div",{children:Object(_.jsx)(_e.a.Menu,{position:"right",children:Object(_.jsx)(_e.a.Item,{children:Object(_.jsx)(Ne.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(_.jsx)(ze.a,{item:!0,text:"Action",children:Object(_.jsxs)(ze.a.Menu,{style:{marginTop:"10px"},children:[Object(_.jsxs)(ze.a.Item,{onClick:function(){return t=e.tableName,void k.a.get("".concat(C,"export/download/").concat(t),{headers:{Authorization:"Bearer ".concat(R)},responseType:"blob"}).then((function(e){var a=e.data,r=new Blob([a],{type:"application/octet-stream"});Ce.a.saveAs(r,"".concat(t))})).catch((function(e){}));var t},children:[Object(_.jsx)(Ae.a,{color:"primary"})," Download File"]}),Object(_.jsxs)(ze.a.Item,{onClick:function(){return t=e.tableName,a=e.organisationUnitId,P(!0),void k.a.get("".concat(C,"export/send-data?fileName=").concat(t,"&facilityId=").concat(a),t,{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){window.setTimeout((function(){s.b.success(" Uploading To server Successful!"),P(!1),Z()}),1e3)})).catch((function(e){if(e.response&&e.response.data){var t=e.response.data.apierror&&""!==e.response.data.apierror.message?e.response.data.apierror.message:"Something went wrong, please try again";s.b.error(t),P(!1)}else P(!1),s.b.error("Something went wrong. Please try again...")}));var t,a},children:[Object(_.jsx)(L.a,{color:"primary"})," Send To Server"]})]})})})})})})}})),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!1,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}}),Object(_.jsx)(de.a,{isOpen:p,toggle:g,className:e.className,size:"lg",backdrop:"static",children:Object(_.jsxs)(je.a,{children:[Object(_.jsx)(fe.a,{toggle:g,children:"Generate JSON Files"}),Object(_.jsx)(Oe.a,{children:Object(_.jsx)(pe.a,{children:Object(_.jsxs)(he.a,{children:[Object(_.jsx)(ge.a,{children:Object(_.jsx)(xe.a,{md:12,children:Object(_.jsxs)(me.a,{children:[Object(_.jsx)(ve.a,{children:"Facility *"}),Object(_.jsxs)(ye.a,{type:"select",name:"facilityId",id:"facilityId",onChange:function(e){I(Object(b.a)(Object(b.a)({},F),{},Object(w.a)({},e.target.name,e.target.value)))},style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:F.facilityId,children:[Object(_.jsx)("option",{children:" "}),d.map((function(e){var t=e.label,a=e.value;return Object(_.jsx)("option",{value:a,children:t},a)}))]}),""!==H.facilityId?Object(_.jsx)("span",{className:t.error,children:H.facilityId}):""]})})}),Object(_.jsx)("br",{}),E?Object(_.jsx)(N,{percentage:K}):"",Object(_.jsx)("br",{}),Object(_.jsx)(ue.a,{type:"submit",variant:"contained",color:"primary",className:t.button,style:{backgroundColor:"#014d88",fontWeight:"bolder"},startIcon:Object(_.jsx)(ke.a,{}),onClick:te,children:E?Object(_.jsx)("span",{style:{textTransform:"capitalize"},children:"Generating Please Wait..."}):Object(_.jsx)("span",{style:{textTransform:"capitalize"},children:"Generate"})}),Object(_.jsx)(ue.a,{variant:"contained",color:"default",onClick:g,className:t.button,style:{backgroundColor:"#992E62"},startIcon:Object(_.jsx)(Se.a,{}),children:Object(_.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"cancel"})})]})})})]})}),Object(_.jsx)(de.a,{isOpen:B,toggle:function(){return P(!B)},backdrop:!1,fade:!0,style:{marginTop:"250px"},size:"lg",children:Object(_.jsx)(Oe.a,{children:Object(_.jsx)("h1",{children:"Uploading File To Server. Please wait..."})})})]})},Ie=a(716),De=Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}}})),Ue=function(e){var t,a=De(),c=Object(r.useState)(!1),n=Object(l.a)(c,2),i=(n[0],n[1],Object(r.useState)({username:"",password:"",url:""})),o=Object(l.a)(i,2),u=o[0],d=o[1],j=Object(r.useState)(!1),f=Object(l.a)(j,2),O=f[0],p=f[1],h=Object(r.useState)([]),g=Object(l.a)(h,2),x=(g[0],g[1]),m=Object(r.useState)({}),v=Object(l.a)(m,2),B=v[0],A=v[1];function P(){return(P=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:k.a.get("".concat(C,"sync/remote-urls"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){x(Object.entries(e.data).map((function(e){var t=Object(l.a)(e,2),a=(t[0],t[1]);return{label:a.url,value:a.id}})))})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Object(r.useEffect)((function(){!function(){P.apply(this,arguments)}()}),[]);var N=function(e){d(Object(b.a)(Object(b.a)({},u),{},Object(w.a)({},e.target.name,e.target.value)))};return Object(_.jsx)("div",{children:Object(_.jsx)(de.a,(t={isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:!1},Object(w.a)(t,"backdrop","static"),Object(w.a)(t,"children",Object(_.jsxs)(je.a,{children:[Object(_.jsx)(fe.a,{toggle:e.toggleModal,children:"Personal Access Token "}),Object(_.jsx)(Oe.a,{children:Object(_.jsx)(pe.a,{children:Object(_.jsxs)(he.a,{children:[Object(_.jsxs)(ge.a,{children:[Object(_.jsx)(xe.a,{md:12,children:Object(_.jsxs)(me.a,{children:[Object(_.jsx)(ve.a,{children:"Server URL * "}),Object(_.jsx)(ye.a,{type:"text",name:"url",id:"url",value:u.url,onChange:N,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==B.url?Object(_.jsx)("span",{className:a.error,children:B.url}):""]})}),Object(_.jsx)(xe.a,{md:12,children:Object(_.jsxs)(me.a,{children:[Object(_.jsx)(ve.a,{children:"Username "}),Object(_.jsx)(ye.a,{type:"text",name:"username",id:"username",value:u.username,onChange:N,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==B.username?Object(_.jsx)("span",{className:a.error,children:B.username}):""]})}),Object(_.jsx)(xe.a,{md:12,children:Object(_.jsxs)(me.a,{children:[Object(_.jsx)(ve.a,{children:"Password "}),Object(_.jsx)(ye.a,{type:"password",name:"password",id:"password",value:u.password,onChange:N,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==B.password?Object(_.jsx)("span",{className:a.error,children:B.password}):""]})})]}),O?Object(_.jsx)(Ie.a,{}):"",Object(_.jsx)("br",{}),Object(_.jsx)(ue.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:function(t){t.preventDefault(),function(){var e=Object(b.a)({},B);return e.username=u.username?"":"Username is required",e.password=u.password?"":"Password is required",e.url=u.url?"":"Server URL is required",A(Object(b.a)({},e)),Object.values(e).every((function(e){return""===e}))}()&&(p(!0),k.a.post("".concat(C,"sync/remote-access-token"),u,{headers:{Authorization:"Bearer ".concat(R)}}).then((function(t){p(!1),e.ServerUrl(),s.b.success("Token Generated Successful"),e.toggleModal()})).catch((function(t){p(!1),s.b.error("Something went wrong"),e.toggleModal()})))},children:Object(_.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Connect & Generate Token"})})]})})})]})),t))})},Ee=(a(657),{Add:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(F.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Check:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(E.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Clear:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Delete:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(K.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),DetailPanel:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Edit:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(X.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Export:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ie.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Filter:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(Z.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),FirstPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ee.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),LastPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ae.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),NextPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(H.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),PreviousPage:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(W.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ResetSearch:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(J.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),Search:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(se.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),SortArrow:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(D.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ThirdStateCheck:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(ce.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))})),ViewColumn:Object(r.forwardRef)((function(e,t){return Object(_.jsx)(be.a,Object(b.a)(Object(b.a)({},e),{},{ref:t}))}))}),Me=Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"}}})),We=function(e){Object(o.g)(),Me();var t=Object(r.useState)([]),a=Object(l.a)(t,2),n=(a[0],a[1],Object(r.useState)([])),i=Object(l.a)(n,2),s=(i[0],i[1],Object(r.useState)([])),b=Object(l.a)(s,2),u=b[0],d=b[1],j=Object(r.useState)(!1),f=Object(l.a)(j,2),O=(f[0],f[1],c.a.useState(!1)),p=Object(l.a)(O,2),h=p[0],g=p[1],x=Object(r.useState)({facility:"",url:""}),m=Object(l.a)(x,2),v=(m[0],m[1],Object(r.useState)(!1)),w=Object(l.a)(v,2);w[0],w[1];function B(){return P.apply(this,arguments)}function P(){return(P=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:k.a.get("".concat(C,"sync/remote-urls"),{headers:{Authorization:"Bearer ".concat(R)}}).then((function(e){d(e.data)})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Object(r.useEffect)((function(){B()}),[]);return Object(_.jsxs)("div",{children:[Object(_.jsx)(ue.a,{variant:"contained",color:"primary",className:" float-right mr-1",style:{backgroundColor:"#014d88"},onClick:function(){g(!h)},children:Object(_.jsx)("span",{style:{textTransform:"capitalize"},children:"New Personal Access Token "})}),Object(_.jsx)("br",{}),Object(_.jsx)("br",{}),Object(_.jsx)("br",{}),Object(_.jsx)(A.a,{icons:Ee,title:"Personal Access Token List",columns:[{title:"URLS",field:"name"},{title:"Username",field:"url",filtering:!1},{title:" Status",field:"date",filtering:!1}],data:u.map((function(e){return{name:e.url,url:e.username,date:"Active",actions:""}})),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!1,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}}),Object(_.jsx)(Ue,{toggleModal:function(){return g(!h)},showModal:h,ServerUrl:B})]})},qe=function(e){var t=Object(r.useState)({username:"",password:"",url:""}),a=Object(l.a)(t,2),c=(a[0],a[1],Object(r.useState)(!1)),n=Object(l.a)(c,2),i=(n[0],n[1],Object(r.useState)([])),o=Object(l.a)(i,2),s=(o[0],o[1]);function b(){return(b=Object(S.a)(Object(y.a)().mark((function e(){return Object(y.a)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:k.a.get("".concat(C,"sync/remote-urls")).then((function(e){console.log(e.data),s(Object.entries(e.data).map((function(e){var t=Object(l.a)(e,2),a=(t[0],t[1]);return{label:a.url,value:a.id}})))})).catch((function(e){}));case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Object(r.useEffect)((function(){!function(){b.apply(this,arguments)}()}),[]);return Object(_.jsx)(We,{})},He=a(513),Ge=a.n(He),Je=a(512),Ve=a.n(Je);a(141),Object(d.a)((function(e){return{card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}}})),a(516),a(517),Object(d.a)((function(e){return{root:{width:"100%",maxWidth:360,backgroundColor:e.palette.background.paper,"& > * + *":{marginTop:e.spacing(2)}}}}));var Ke=["children","value","index"];x.a.locale("en"),v()();var Qe=Object(d.a)((function(e){return{header:{fontSize:"20px",fontWeight:"bold",padding:"5px",paddingBottom:"10px"},inforoot:{margin:"5px"},dropdown:{marginTop:"50px"},paper:{marginRight:e.spacing(2)},downmenu:{display:"flex"}}}));function Xe(e){var t=e.children,a=e.value,r=e.index,c=Object(u.a)(e,Ke);return Object(_.jsx)(p.a,Object(b.a)(Object(b.a)({component:"div",role:"tabpanel",hidden:a!==r,id:"scrollable-force-tabpanel-".concat(r),"aria-labelledby":"scrollable-force-tab-".concat(r)},c),{},{children:a===r&&Object(_.jsx)(h.a,{p:5,children:t})}))}function Ye(e){return{id:"scrollable-force-tab-".concat(e),"aria-controls":"scrollable-force-tabpanel-".concat(e)}}var Ze=function(e){var t=Qe(),a=Object(r.useState)(null),c=Object(l.a)(a,2),n=c[0],i=c[1],o=e.location&&e.location.state?e.location.state:" ",s=function(e,t){var a=t,r=new RegExp("[?&]"+e+"=([^&#]*)","i").exec(a);return r?r[1]:null}("tab",e.location&&e.location.search?e.location.search:""),u=null!==s?s:o;return Object(r.useEffect)((function(){switch(u){case"database-sync":default:return i(0);case"setting":return i(1)}}),[s]),Object(_.jsxs)(_.Fragment,{children:[Object(_.jsx)("div",{className:"row page-titles mx-0",style:{marginTop:"0px",marginBottom:"-10px"},children:Object(_.jsx)("ol",{className:"breadcrumb",children:Object(_.jsx)("li",{className:"breadcrumb-item active",children:Object(_.jsx)("h4",{children:"Central Sync"})})})}),Object(_.jsx)("br",{}),Object(_.jsxs)("div",{className:t.root,children:[Object(_.jsx)(j.a,{position:"static",style:{backgroundColor:"#fff"},children:Object(_.jsxs)(f.a,{value:n,onChange:function(e,t){i(t)},variant:"scrollable",scrollButtons:"on",indicatorColor:"secondary",textColor:"primary","aria-label":"scrollable force tabs example",children:[Object(_.jsx)(O.a,Object(b.a)({className:t.title,label:"Generate & Upload JSON Files",icon:Object(_.jsx)(Ve.a,{})},Ye(0))),Object(_.jsx)(O.a,Object(b.a)({className:t.title,label:"Configuration  ",icon:Object(_.jsx)(Ge.a,{})},Ye(1)))]})}),Object(_.jsx)(Xe,{value:n,index:0,children:Object(_.jsx)(Fe,{})}),Object(_.jsx)(Xe,{value:n,index:1,children:Object(_.jsx)(qe,{})})]})]})};function $e(){return Object(_.jsx)(o.a,{children:Object(_.jsxs)("div",{children:[Object(_.jsx)(s.a,{}),Object(_.jsx)(o.d,{children:Object(_.jsx)(o.b,{path:"/",children:Object(_.jsx)(Ze,{})})})]})})}var et=a(183),tt=function(e){e&&e instanceof Function&&a.e(6).then(a.bind(null,894)).then((function(t){var a=t.getCLS,r=t.getFID,c=t.getFCP,n=t.getLCP,i=t.getTTFB;a(e),r(e),c(e),n(e),i(e)}))},at=a(514),rt="ltr",ct=[{typography:"poppins",version:"light",layout:"vertical",headerBg:"color_1",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"full",direction:rt},{typography:"poppins",version:"light",layout:"vertical",primary:"color_5",headerBg:"color_5",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",direction:rt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_11",headerBg:"color_1",sidebarBg:"color_11",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_11",direction:rt},{typography:"poppins",version:"dark",layout:"vertical",headerBg:"color_3",navheaderBg:"color_3",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_1",direction:rt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_15",headerBg:"color_1",sidebarStyle:"full",sidebarBg:"color_1",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_15",direction:rt},{typography:"poppins",version:"light",layout:"horizontal",navheaderBg:"color_1",headerBg:"color_1",sidebarBg:"color_9",sidebarStyle:"modern",sidebarPosition:"static",headerPosition:"fixed",containerLayout:"wide",primary:"color_9",direction:rt}],nt=Object(r.createContext)(),it=function(e){var t=Object(r.useState)({value:"full",label:"Full"}),a=Object(l.a)(t,2),c=a[0],n=a[1],i=Object(r.useState)({value:"fixed",label:"Fixed"}),o=Object(l.a)(i,2),s=o[0],b=o[1],u=Object(r.useState)({value:"fixed",label:"Fixed"}),d=Object(l.a)(u,2),j=d[0],f=d[1],O=Object(r.useState)({value:"vertical",label:"Vertical"}),p=Object(l.a)(O,2),h=p[0],g=p[1],x=Object(r.useState)({value:"ltr",label:"LTR"}),m=Object(l.a)(x,2),v=m[0],y=m[1],w=Object(r.useState)("color_1"),S=Object(l.a)(w,2),B=S[0],A=S[1],P=Object(r.useState)("color_1"),k=Object(l.a)(P,2),R=k[0],C=k[1],N=Object(r.useState)("color_1"),z=Object(l.a)(N,2),L=z[0],T=z[1],F=Object(r.useState)("color_1"),I=Object(l.a)(F,2),D=I[0],U=I[1],E=Object(r.useState)(!1),M=Object(l.a)(E,2),W=M[0],q=M[1],H=Object(r.useState)(!1),G=Object(l.a)(H,2),J=G[0],V=G[1],K=Object(r.useState)({value:"light",label:"Light"}),Q=Object(l.a)(K,2),X=Q[0],Y=Q[1],Z=Object(r.useState)({value:"wide-boxed",label:"Wide Boxed"}),$=Object(l.a)(Z,2),ee=$[0],te=$[1],ae=document.querySelector("body"),re=Object(r.useState)(0),ce=Object(l.a)(re,2),ne=ce[0],ie=ce[1],oe=Object(r.useState)(0),se=Object(l.a)(oe,2),le=se[0],be=se[1],ue=function(e){A(e),ae.setAttribute("data-primary",e)},de=function(e){C(e),ae.setAttribute("data-nav-headerbg",e)},je=function(e){T(e),ae.setAttribute("data-headerbg",e)},fe=function(e){U(e),ae.setAttribute("data-sibebarbg",e)},Oe=function(e){b(e),ae.setAttribute("data-sidebar-position",e.value)},pe=function(e){y(e),ae.setAttribute("direction",e.value);var t=document.querySelector("html");t.setAttribute("dir",e.value),t.className=e.value},he=function(e){"horizontal"===e.value&&"overlay"===c.value?(g(e),ae.setAttribute("data-layout",e.value),n({value:"full",label:"Full"}),ae.setAttribute("data-sidebar-style","full")):(g(e),ae.setAttribute("data-layout",e.value))},ge=function(e){"horizontal"===h.value&&"overlay"===e.value?alert("Sorry! Overlay is not possible in Horizontal layout."):(n(e),q("icon-hover"===e.value?"_i-hover":""),ae.setAttribute("data-sidebar-style",e.value))},xe=function(e){f(e),ae.setAttribute("data-header-position",e.value)},me=function(e){ae.setAttribute("data-theme-version",e.value),Y(e)},ve=function(e){te(e),ae.setAttribute("data-container",e.value),"boxed"===e.value&&ge({value:"overlay",label:"Overlay"})};return Object(r.useEffect)((function(){var e=document.querySelector("body");e.setAttribute("data-typography","poppins"),e.setAttribute("data-theme-version","light"),e.setAttribute("data-layout","vertical"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-nav-headerbg","color_1"),e.setAttribute("data-headerbg","color_1"),e.setAttribute("data-sidebar-style","overlay"),e.setAttribute("data-sibebarbg","color_1"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-sidebar-position","fixed"),e.setAttribute("data-header-position","fixed"),e.setAttribute("data-container","wide"),e.setAttribute("direction","ltr");var t=function(){ie(window.innerWidth),be(window.innerHeight),window.innerWidth>=768&&window.innerWidth<1024?e.setAttribute("data-sidebar-style","mini"):window.innerWidth<=768?e.setAttribute("data-sidebar-style","overlay"):e.setAttribute("data-sidebar-style","full")};return t(),window.addEventListener("resize",t),function(){return window.removeEventListener("resize",t)}}),[]),Object(_.jsx)(nt.Provider,{value:{body:ae,sideBarOption:[{value:"compact",label:"Compact"},{value:"full",label:"Full"},{value:"mini",label:"Mini"},{value:"modern",label:"Modern"},{value:"overlay",label:"Overlay"},{value:"icon-hover",label:"Icon-hover"}],layoutOption:[{value:"vertical",label:"Vertical"},{value:"horizontal",label:"Horizontal"}],backgroundOption:[{value:"light",label:"Light"},{value:"dark",label:"Dark"}],sidebarposition:s,headerPositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],containerPosition:[{value:"wide-boxed",label:"Wide Boxed"},{value:"boxed",label:"Boxed"},{value:"wide",label:"Wide"}],directionPosition:[{value:"ltr",label:"LTR"},{value:"rtl",label:"RTL"}],fontFamily:[{value:"poppins",label:"Poppins"},{value:"roboto",label:"Roboto"},{value:"cairo",label:"Cairo"},{value:"opensans",label:"Open Sans"},{value:"HelveticaNeue",label:"HelveticaNeue"}],primaryColor:B,navigationHader:R,windowWidth:ne,windowHeight:le,changePrimaryColor:ue,changeNavigationHader:de,changeSideBarStyle:ge,sideBarStyle:c,changeSideBarPostion:Oe,sidebarpositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],changeHeaderPostion:xe,headerposition:j,changeSideBarLayout:he,sidebarLayout:h,changeDirectionLayout:pe,changeContainerPosition:ve,direction:v,colors:["color_1","color_2","color_3","color_4","color_5","color_6","color_7","color_8","color_9","color_10","color_11","color_12","color_13","color_14","color_15"],haderColor:L,chnageHaderColor:je,chnageSidebarColor:fe,sidebarColor:D,iconHover:W,menuToggle:J,openMenuToggle:function(){"overly"===c.value?V(!0):V(!1)},changeBackground:me,background:X,containerPosition_:ee,setDemoTheme:function(e,t){var a={},r=ct[e];ae.setAttribute("data-typography",r.typography),a.value=r.version,me(a),a.value=r.layout,he(a),ue(r.primary),de(r.navheaderBg),je(r.headerBg),a.value=r.sidebarStyle,ge(a),fe(r.sidebarBg),a.value=r.sidebarPosition,Oe(a),a.value=r.headerPosition,xe(a),a.value=r.containerLayout,ve(a),a.value=t,pe(a)}},children:e.children})};i.a.render(Object(_.jsx)(c.a.StrictMode,{children:Object(_.jsx)(at.a,{children:Object(_.jsx)(et.a,{basename:"/",children:Object(_.jsx)(it,{children:Object(_.jsx)($e,{})})})})}),document.getElementById("root")),tt()}},[[656,1,2]]]);
//# sourceMappingURL=main.30f47a63.chunk.js.map
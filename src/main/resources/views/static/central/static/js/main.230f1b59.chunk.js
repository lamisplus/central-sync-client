(this.webpackJsonpLamisPlus=this.webpackJsonpLamisPlus||[]).push([[0],{549:function(e,t,a){},550:function(e,t,a){},651:function(e,t,a){"use strict";a.r(t);var r=a(0),c=a.n(r),i=a(21),o=a.n(i),l=a(47),s=a(55),n=(a(157),a(549),a(550),a(551),a(464)),d=a(703),j=a(721),b=a(702),u=a(131),f=a(369),O=a(243),h=a.n(O),p=a(502),g=a.n(p);var x=a(102),m=a.n(x),y=a(41),v=a.n(y);const w=new URLSearchParams(window.location.search).get("jwt"),S="/api/v1/";var R=a(4);var C=e=>{let{percentage:t}=e;return Object(R.jsx)("div",{className:"progress",children:Object(R.jsxs)("div",{className:"progress-bar progress-bar-striped bg-success",role:"progressbar",style:{width:"".concat(t,"%"),height:"80px"},children:[t,"%"]})})},k=a(305),A=a.n(k),N=a(105),B=a.n(N),P=a(115),z=a.n(P),F=a(106),I=a.n(F),T=a(113),_=a.n(T),D=a(76),L=a.n(D),M=a(75),E=a.n(M),K=a(107),U=a.n(K),W=a(108),H=a.n(W),V=a(110),q=a.n(V),G=a(111),J=a.n(G),Y=a(112),Q=a.n(Y),X=a(116),Z=a.n(X),$=a(109),ee=a.n($),te=a(114),ae=a.n(te),re=a(117),ce=a.n(re),ie=a(465),oe=(a(371),a(372),a(725)),le=a(707),se=a(708),ne=a(709),de=a(710),je=a(711),be=a(712),ue=a(713),fe=a(714),Oe=a(715),he=a(716),pe=a(182),ge=a.n(pe),xe=a(517),me=a.n(xe),ye=a(171),ve=a.n(ye),we=a(288),Se=a.n(we),Re=a(506),Ce=a.n(Re),ke=(a(373),a(720)),Ae=a(722),Ne=a(719);a(494);const Be={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(_.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))},Pe=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"}})));var ze=e=>{Pe();const t=null===e.errorLogsToDisplay?[]:e.errorLogsToDisplay;return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:!1,backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{toggle:e.toggleModal,children:"Logs "}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(m.a,{icons:Be,title:"JSON Files Errors ",columns:[{title:"Name",field:"name",filtering:!1},{title:"Error",field:"error",filtering:!1},{title:"Others",field:"others",filtering:!1}],data:t.map((e=>({name:e.name,error:e.message,others:e.others}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})})]})})})},Fe=a(717);const Ie=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}})));var Te=e=>{const t=null!==e.rowObj?e.rowObj:{},a=Ie(),[c,i]=Object(r.useState)(!1),o={username:"",password:"",syncHistoryUuid:t.id,syncHistoryTrackerUuid:t.uuid,facilityId:t.organisationUnitId},[l,n]=Object(r.useState)(o),[d,j]=Object(r.useState)(!1),[b,u]=Object(r.useState)([]),[f,O]=Object(r.useState)({});Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"sync/remote-urls"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{u(Object.entries(e.data).map((e=>{let[t,a]=e;return{label:a.url,value:a.id}})))})).catch((e=>{}))}()}),[]);const h=e=>{n({...l,[e.target.name]:e.target.value})};return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:!1,backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{toggle:e.toggleModal,children:"SEND TO SERVER "}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Username "}),Object(R.jsx)(he.a,{type:"text",name:"username",id:"username",value:l.username,onChange:h,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==f.username?Object(R.jsx)("span",{className:a.error,children:f.username}):""]})}),Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Password "}),Object(R.jsx)(he.a,{type:"password",name:"password",id:"password",value:l.password,onChange:h,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==f.password?Object(R.jsx)("span",{className:a.error,children:f.password}):""]})})]}),d?Object(R.jsx)(Fe.a,{}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:t=>{t.preventDefault(),(()=>{let e={...f};return e.username=l.username?"":"Username is required",e.password=l.password?"":"Password is required",O({...e}),Object.values(e).every((e=>""===e))})()&&(j(!0),v.a.post("".concat(S,"export/file/data"),l,{headers:{Authorization:"Bearer ".concat(w)}}).then((t=>{j(!1),e.ServerUrl(),s.b.success("Token Generated Successful"),e.toggleModal()})).catch((t=>{j(!1),s.b.error("Something went wrong"),e.toggleModal()})))},children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Send To Server"})})]})})})]})})})},_e=a(718);const De=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"}})));var Le=e=>{const t=De();return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:!1,backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{toggle:e.toggleModal,children:"GENERATE KEY"}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsx)(_e.a,{color:"primary",children:Object(R.jsxs)("p",{style:{marginTop:".7rem"},children:["Info : \xa0\xa0\xa0",Object(R.jsx)("span",{style:{fontWeight:"bolder"},children:"Kindly copy the generated key for upload"}),"\xa0\xa0\xa0\xa0\xa0\xa0"]})})}),Object(R.jsx)(ue.a,{md:6})]}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{variant:"contained",color:"default",onClick:e.togglestatus,className:t.button,startIcon:Object(R.jsx)(ge.a,{}),children:"Cancel"})]})})})]})})})};const Me={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(_.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))},Ee=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}})));var Ke=e=>{const t=Ee(),[a,c]=Object(r.useState)([]),[i,o]=Object(r.useState)([]),[l,n]=Object(r.useState)(!1),[d,j]=Object(r.useState)(!1),[b,u]=Object(r.useState)(!1),[f,O]=Object(r.useState)(!1),[p,g]=Object(r.useState)([]),x=()=>n(!l),[y,k]=Object(r.useState)(!1),[N,B]=Object(r.useState)({facilityId:"",startDate:"",endDate:"",all:!1}),[P,z]=Object(r.useState)(!1),[F,I]=Object(r.useState)({}),[T,_]=Object(r.useState)(0),[D,L]=Object(r.useState)(!1),[M,E]=Object(r.useState)([]),[K,U]=Object(r.useState)(),[W,H]=Object(r.useState)(null);Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"account"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{o(Object.entries(e.data.applicationUserOrganisationUnits).map((e=>{let[t,a]=e;return{label:a.organisationUnitName,value:a.organisationUnitId}})))})).catch((e=>{}))}(),V()}),[]);async function V(){v.a.get("".concat(S,"export/sync-histories"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{c(e.data)})).catch((e=>{}))}return Object(R.jsxs)("div",{children:[!D&&Object(R.jsxs)(R.Fragment,{children:[Object(R.jsx)(ie.a,{variant:"contained",style:{backgroundColor:"#014d88"},className:" float-right mr-1",onClick:()=>{n(!l)},children:Object(R.jsx)("span",{style:{textTransform:"capitalize",color:"#fff"},children:"Generate JSON Files "})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)(m.a,{icons:Me,title:"Generated JSON Files List ",columns:[{title:"Facility Name",field:"facilityName"},{title:"File Name ",field:"tableName",filtering:!1},{title:"Upload Size ",field:"uploadSize",filtering:!1},{title:"Date Generated ",field:"date",filtering:!1},{title:"Status",field:"status",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:a.map((e=>({facilityName:e.facilityName,tableName:e.tableName,uploadSize:e.uploadSize,date:h()(e.dateLastSync).format("LLLL"),status:null===e.errorLog?0===e.processed?"Processing":"Completed":"Error",actions:Object(R.jsx)("div",{children:Object(R.jsx)(ke.a.Menu,{position:"right",children:Object(R.jsx)(ke.a.Item,{children:Object(R.jsx)(Ae.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(R.jsx)(Ne.a,{item:!0,text:"Action",children:Object(R.jsxs)(Ne.a.Menu,{style:{marginTop:"10px"},children:[Object(R.jsxs)(Ne.a.Item,{onClick:()=>{return t=e.tableName,void v.a.get("".concat(S,"export/download/").concat(t),{headers:{Authorization:"Bearer ".concat(w)},responseType:"blob"}).then((e=>{const a=e.data;let r=new Blob([a],{type:"application/octet-stream"});Ce.a.saveAs(r,"".concat(t))})).catch((e=>{}));var t},children:[Object(R.jsx)(me.a,{color:"primary"})," Download File"]}),Object(R.jsxs)(Ne.a.Item,{onClick:()=>(e=>{u(!b),H(e)})(e.tableName,e.organisationUnitId),children:[Object(R.jsx)(A.a,{color:"primary"})," Send To Server"]}),Object(R.jsxs)(Ne.a.Item,{onClick:()=>{j(!d)},children:[Object(R.jsx)(ve.a,{color:"primary"}),"View Generate Key"]}),Object(R.jsxs)(Ne.a.Item,{onClick:()=>(e=>{g(e.errorLog),O(!f)})(e),children:[Object(R.jsx)(ve.a,{color:"primary"}),"View Logs"]})]})})})})})})}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})]}),Object(R.jsx)(oe.a,{isOpen:l,toggle:x,className:e.className,size:"lg",backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{toggle:x,children:"Generate JSON Files"}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Facility *"}),Object(R.jsxs)(he.a,{type:"select",name:"facilityId",id:"facilityId",onChange:e=>{B({...N,[e.target.name]:e.target.value})},style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:N.facilityId,children:[Object(R.jsx)("option",{children:" "}),i.map((e=>{let{label:t,value:a}=e;return Object(R.jsx)("option",{value:a,children:t},a)}))]}),""!==F.facilityId?Object(R.jsx)("span",{className:t.error,children:F.facilityId}):""]})}),Object(R.jsxs)("div",{className:"form-check custom-checkbox ml-3 ",children:[Object(R.jsx)("input",{type:"checkbox",className:"form-check-input",name:"all",id:"all",onChange:e=>{e.target.checked?B({...N,all:e.target.checked}):B({...N,all:!1})},checked:N.all}),Object(R.jsx)("label",{className:"form-check-label",htmlFor:"all",children:"Recent Update ?"})]})]}),Object(R.jsx)("br",{}),Object(R.jsx)("b",{children:!0===N.all?"Only the updated records will be pushed":"You are pushing record from initial"}),Object(R.jsx)("br",{}),P?Object(R.jsx)(C,{percentage:T}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",color:"primary",className:t.button,style:{backgroundColor:"#014d88",fontWeight:"bolder"},startIcon:Object(R.jsx)(Se.a,{}),onClick:async e=>{if(e.preventDefault(),z(!0),(()=>{let e={...F};return e.facilityId=N.facilityId?"":"Facility is required",I({...e}),Object.values(e).every((e=>""===e))})())try{await v.a.get("".concat(S,"export/all?facilityId=").concat(N.facilityId,"&current=").concat(N.all),{headers:{Authorization:"Bearer ".concat(w)},onUploadProgress:e=>{_(parseInt(Math.round(100*e.loaded/e.total))),setTimeout((()=>_(0)),1e4)}});s.b.success("JSON Extraction was successful!"),x(),V(),z(!1)}catch(t){z(!1)}else s.b.error("Please select facility")},children:P?Object(R.jsx)("span",{style:{textTransform:"capitalize"},children:"Generating Please Wait..."}):Object(R.jsx)("span",{style:{textTransform:"capitalize"},children:"Generate"})}),Object(R.jsx)(ie.a,{variant:"contained",color:"default",onClick:x,className:t.button,style:{backgroundColor:"#992E62"},startIcon:Object(R.jsx)(ge.a,{}),children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"cancel"})})]})})})]})}),Object(R.jsx)(oe.a,{isOpen:y,toggle:()=>k(!y),backdrop:!1,fade:!0,style:{marginTop:"250px"},size:"lg",children:Object(R.jsx)(ne.a,{children:Object(R.jsx)("h1",{children:"Uploading File To Server. Please wait..."})})}),Object(R.jsx)(Te,{toggleModal:()=>u(!b),showModal:b,rowObj:W}),Object(R.jsx)(ze,{toggleModal:()=>O(!f),showModal:f,errorLogsToDisplay:p}),Object(R.jsx)(Le,{toggleModal:()=>j(!d),showModal:d})]})};const Ue=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}})));var We=e=>{const t=Ue(),[a,c]=Object(r.useState)(!1),[i,o]=Object(r.useState)({file:""}),[l,n]=Object(r.useState)(!1),[d,j]=Object(r.useState)({}),[b,u]=Object(r.useState)(null),[f,O]=Object(r.useState)(null);return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:!1,backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{toggle:e.toggleModal,children:"Personal Access Token "}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsx)(be.a,{children:Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Config File"}),Object(R.jsx)(he.a,{type:"file",name:"file",id:"file",value:i.file,onChange:e=>(t=>{const a=new FileReader;a.onloadend=()=>{try{u(JSON.parse(a.result)),O(a.result)}catch(e){u("**Not valid JSON file!**")}},void 0!==t&&a.readAsText(t)})(e.target.files[0]),style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==d.file?Object(R.jsx)("span",{className:t.error,children:d.file}):""]})})}),l?Object(R.jsx)(Fe.a,{}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:t=>{t.preventDefault(),n(!0),v.a.post("".concat(S,"sync/sync-config"),b,{headers:{Authorization:"Bearer ".concat(w)}}).then((t=>{n(!1),e.ServerUrl(),s.b.success("Config File Uploaded Successful"),e.toggleModal()})).catch((t=>{n(!1),s.b.error("Something went wrong"),e.toggleModal()}))},children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Upload Config File"})})]})})})]})})})};const He={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(_.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))};var Ve=e=>{const[t,a]=Object(r.useState)([]),[i,o]=Object(r.useState)(!1),[l,s]=c.a.useState(!1),[n,d]=Object(r.useState)(null),[j,b]=Object(r.useState)(!1);Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"sync/sync-config"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{a(e.data)})).catch((e=>{}))}()}),[]);return Object(R.jsxs)("div",{children:[Object(R.jsx)(ie.a,{variant:"contained",color:"primary",className:" float-right mr-1",style:{backgroundColor:"#014d88"},onClick:j?()=>{b(!1)}:()=>{s(!l)},children:Object(R.jsxs)("span",{style:{textTransform:"capitalize"},children:[j?"<< Back":"Upload Config File"," "]})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),!j&&Object(R.jsx)(m.a,{icons:He,title:"Config Information ",columns:[{title:" File Name",field:"name"},{title:"Version",field:"version"},{title:"Release Date",field:"releaseDate",filtering:!1},{title:"Upload Date",field:"uploadDate",filtering:!1},{title:"Status",field:"status",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:t.map((e=>({name:e.name,version:e.version,releaseDate:e.releaseDate,uploadDate:e.uploadDate,status:!0===e.active?"Active":"previous",actions:Object(R.jsx)("div",{children:Object(R.jsx)(ke.a.Menu,{position:"right",children:Object(R.jsx)(ke.a.Item,{children:Object(R.jsx)(Ae.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(R.jsx)(Ne.a,{item:!0,text:"Action",children:Object(R.jsx)(Ne.a.Menu,{style:{marginTop:"10px"},children:Object(R.jsxs)(Ne.a.Item,{onClick:()=>(e=>{b(!0),d(e)})(e),children:[Object(R.jsx)(ve.a,{})," View "]})})})})})})})}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!1,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}}),j&&Object(R.jsx)(R.Fragment,{children:Object(R.jsx)(m.a,{icons:He,title:"Config Information - "+n.name,columns:[{title:" Module Name",field:"moduleName"},{title:"Version",field:"version"}],data:n.configModules.map((e=>({moduleName:e.moduleName,version:e.version}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!1,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})}),Object(R.jsx)(We,{toggleModal:()=>s(!l),showModal:l,ServerUrl:t})]})};var qe=e=>{const[t,a]=Object(r.useState)({username:"",password:"",url:""}),[c,i]=Object(r.useState)(!1),[o,l]=Object(r.useState)([]);Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"sync/remote-urls")).then((e=>{console.log(e.data),l(Object.entries(e.data).map((e=>{let[t,a]=e;return{label:a.url,value:a.id}})))})).catch((e=>{}))}()}),[]);return Object(R.jsx)(Ve,{})};const Ge=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"}})));var Je=e=>{const t=Ge(),[a,c]=Object(r.useState)(null),[i,o]=Object(r.useState)({appKey:"",facilityId:"",id:""}),[l,n]=Object(r.useState)([]),[d,j]=Object(r.useState)(!1),[b,u]=Object(r.useState)({});Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"account"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{n(Object.entries(e.data.applicationUserOrganisationUnits).map((e=>{let[t,a]=e;return{label:a.organisationUnitName,value:a.organisationUnitId}})))})).catch((e=>{}))}(),async function(){v.a.get("".concat(S,"sync/app-key"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{c(e.data)})).catch((e=>{}))}()}),[]);const f=e=>{console.log(e.target.value),console.log(e.target.name),o({...i,[e.target.name]:e.target.value})},O=t=>{t.preventDefault(),(()=>{let e={...b};return e.appKey=i.appKey?"":"Key is required",u({...e}),Object.values(e).every((e=>""===e))})()&&(j(!0),v.a.post("".concat(S,"sync/app-key"),i,{headers:{Authorization:"Bearer ".concat(w)}}).then((t=>{j(!1),s.b.success("APP KEY save successful"),e.showAppKeysListTable(!0),e.AppKeyHistory()})).catch((e=>{j(!1),s.b.error("Something went wrong")})))};return Object(R.jsxs)("div",{children:[Object(R.jsx)("h1",{children:"APP Key"}),Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{variant:"contained",color:"primary",className:" float-right mr-1",style:{backgroundColor:"#014d88"},onClick:()=>{e.showAppKeysListTable(!0)},children:Object(R.jsxs)("span",{style:{textTransform:"capitalize"},children:["<< Back"," "]})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),null===a?Object(R.jsxs)(R.Fragment,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Facility *"}),Object(R.jsxs)(he.a,{type:"select",name:"facilityId",id:"facilityId",onChange:f,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:i.facilityId,children:[Object(R.jsx)("option",{children:" "}),l.map((e=>{let{label:t,value:a}=e;return Object(R.jsx)("option",{value:a,children:t},a)}))]}),""!==b.facilityId?Object(R.jsx)("span",{className:t.error,children:b.facilityId}):""]})}),Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Key"}),Object(R.jsx)(he.a,{type:"text",name:"appKey",id:"appKey",value:i.appKey,onChange:f,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==b.appKey?Object(R.jsx)("span",{className:t.error,children:b.appKey}):""]})})]}),d?Object(R.jsx)(Fe.a,{}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:O,children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Update Key"})})]}):Object(R.jsxs)(R.Fragment,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Facility *"}),Object(R.jsxs)(he.a,{type:"select",name:"facilityId",id:"facilityId",onChange:f,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:i.facilityId,children:[Object(R.jsx)("option",{children:" "}),l.map((e=>{let{label:t,value:a}=e;return Object(R.jsx)("option",{value:a,children:t},a)}))]}),""!==b.facilityId?Object(R.jsx)("span",{className:t.error,children:b.facilityId}):""]})}),Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(fe.a,{children:[Object(R.jsx)(Oe.a,{children:"Key"}),Object(R.jsx)(he.a,{type:"text",name:"appKey",id:"appKey",value:i.appKey,onChange:f,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==b.appKey?Object(R.jsx)("span",{className:t.error,children:b.appKey}):""]})})]}),d?Object(R.jsx)(Fe.a,{}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:O,children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Save Key"})})]})]})};const Ye={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(_.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(E.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))},Qe=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}})));var Xe=e=>{Qe();const[t,a]=Object(r.useState)([]),[c,i]=Object(r.useState)(!0);async function o(){v.a.get("".concat(S,"sync/app-key"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{a(e.data)})).catch((e=>{}))}Object(r.useEffect)((()=>{o()}),[]);return Object(R.jsx)("div",{children:c?Object(R.jsxs)(R.Fragment,{children:[Object(R.jsx)(ie.a,{variant:"contained",style:{backgroundColor:"#014d88"},className:" float-right mr-1",onClick:()=>{i(!1)},children:Object(R.jsx)("span",{style:{textTransform:"capitalize",color:"#fff"},children:"Add App Key "})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)(m.a,{icons:Ye,title:"APP Key List ",columns:[{title:"Facility Name",field:"facilityName"},{title:"ID ",field:"id",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:t.map((e=>({facilityName:e.facilityName,id:e.id,actions:Object(R.jsx)("div",{children:Object(R.jsx)(ke.a.Menu,{position:"right",children:Object(R.jsx)(ke.a.Item,{children:Object(R.jsx)(Ae.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(R.jsx)(Ne.a,{item:!0,text:"Action",children:Object(R.jsx)(Ne.a.Menu,{style:{marginTop:"10px"},children:Object(R.jsxs)(Ne.a.Item,{onClick:()=>{i(!1)},children:[Object(R.jsx)(ve.a,{color:"primary"}),"View key"]})})})})})})})}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})]}):Object(R.jsx)(R.Fragment,{children:Object(R.jsx)(Je,{showAppKeysListTable:i,AppKeyHistory:o})})})},Ze=a(385),$e=a.n(Ze),et=a(518),tt=a.n(et);h.a.locale("en"),g()();const at=Object(n.a)((e=>({header:{fontSize:"20px",fontWeight:"bold",padding:"5px",paddingBottom:"10px"},inforoot:{margin:"5px"},dropdown:{marginTop:"50px"},paper:{marginRight:e.spacing(2)},downmenu:{display:"flex"}})));function rt(e){const{children:t,value:a,index:r,...c}=e;return Object(R.jsx)(u.a,{component:"div",role:"tabpanel",hidden:a!==r,id:"scrollable-force-tabpanel-".concat(r),"aria-labelledby":"scrollable-force-tab-".concat(r),...c,children:a===r&&Object(R.jsx)(f.a,{p:5,children:t})})}function ct(e){return{id:"scrollable-force-tab-".concat(e),"aria-controls":"scrollable-force-tabpanel-".concat(e)}}var it=function(e){const t=at(),[a,c]=Object(r.useState)(null),i=e.location&&e.location.state?e.location.state:" ",o=((e,t)=>{let a=t,r=new RegExp("[?&]"+e+"=([^&#]*)","i").exec(a);return r?r[1]:null})("tab",e.location&&e.location.search?e.location.search:""),l=null!==o?o:i;return Object(r.useEffect)((()=>{switch(l){case"database-sync":default:return c(0);case"setting":return c(1)}}),[o]),Object(R.jsxs)(R.Fragment,{children:[Object(R.jsx)("div",{className:"row page-titles mx-0",style:{marginTop:"0px",marginBottom:"-10px"},children:Object(R.jsx)("ol",{className:"breadcrumb",children:Object(R.jsx)("li",{className:"breadcrumb-item active",children:Object(R.jsx)("h4",{children:"Central Sync"})})})}),Object(R.jsx)("br",{}),Object(R.jsxs)("div",{className:t.root,children:[Object(R.jsx)(d.a,{position:"static",style:{backgroundColor:"#fff"},children:Object(R.jsxs)(j.a,{value:a,onChange:(e,t)=>{c(t)},variant:"scrollable",scrollButtons:"on",indicatorColor:"secondary",textColor:"primary","aria-label":"scrollable force tabs example",children:[Object(R.jsx)(b.a,{className:t.title,label:"Generate & Upload JSON Files",icon:Object(R.jsx)(tt.a,{}),...ct(0)}),Object(R.jsx)(b.a,{className:t.title,label:"APP Key Management",icon:Object(R.jsx)($e.a,{}),...ct(2)}),Object(R.jsx)(b.a,{className:t.title,label:"Configuration  ",icon:Object(R.jsx)($e.a,{}),...ct(1)})]})}),Object(R.jsx)(rt,{value:a,index:0,children:Object(R.jsx)(Ke,{})}),Object(R.jsx)(rt,{value:a,index:1,children:Object(R.jsx)(Xe,{})}),Object(R.jsx)(rt,{value:a,index:2,children:Object(R.jsx)(qe,{})})]})]})};function ot(){return Object(R.jsx)(l.a,{children:Object(R.jsxs)("div",{children:[Object(R.jsx)(s.a,{}),Object(R.jsx)(l.d,{children:Object(R.jsx)(l.b,{path:"/",children:Object(R.jsx)(it,{})})})]})})}var lt=a(382);var st=e=>{e&&e instanceof Function&&a.e(6).then(a.bind(null,834)).then((t=>{let{getCLS:a,getFID:r,getFCP:c,getLCP:i,getTTFB:o}=t;a(e),r(e),c(e),i(e),o(e)}))},nt=a(519);let dt="ltr";const jt=[{typography:"poppins",version:"light",layout:"vertical",headerBg:"color_1",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"full",direction:dt},{typography:"poppins",version:"light",layout:"vertical",primary:"color_5",headerBg:"color_5",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",direction:dt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_11",headerBg:"color_1",sidebarBg:"color_11",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_11",direction:dt},{typography:"poppins",version:"dark",layout:"vertical",headerBg:"color_3",navheaderBg:"color_3",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_1",direction:dt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_15",headerBg:"color_1",sidebarStyle:"full",sidebarBg:"color_1",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_15",direction:dt},{typography:"poppins",version:"light",layout:"horizontal",navheaderBg:"color_1",headerBg:"color_1",sidebarBg:"color_9",sidebarStyle:"modern",sidebarPosition:"static",headerPosition:"fixed",containerLayout:"wide",primary:"color_9",direction:dt}],bt=Object(r.createContext)();var ut=e=>{const[t,a]=Object(r.useState)({value:"full",label:"Full"}),[c,i]=Object(r.useState)({value:"fixed",label:"Fixed"}),[o,l]=Object(r.useState)({value:"fixed",label:"Fixed"}),[s,n]=Object(r.useState)({value:"vertical",label:"Vertical"}),[d,j]=Object(r.useState)({value:"ltr",label:"LTR"}),[b,u]=Object(r.useState)("color_1"),[f,O]=Object(r.useState)("color_1"),[h,p]=Object(r.useState)("color_1"),[g,x]=Object(r.useState)("color_1"),[m,y]=Object(r.useState)(!1),[v,w]=Object(r.useState)(!1),[S,C]=Object(r.useState)({value:"light",label:"Light"}),[k,A]=Object(r.useState)({value:"wide-boxed",label:"Wide Boxed"}),N=document.querySelector("body"),[B,P]=Object(r.useState)(0),[z,F]=Object(r.useState)(0),I=e=>{u(e),N.setAttribute("data-primary",e)},T=e=>{O(e),N.setAttribute("data-nav-headerbg",e)},_=e=>{p(e),N.setAttribute("data-headerbg",e)},D=e=>{x(e),N.setAttribute("data-sibebarbg",e)},L=e=>{i(e),N.setAttribute("data-sidebar-position",e.value)},M=e=>{j(e),N.setAttribute("direction",e.value);let t=document.querySelector("html");t.setAttribute("dir",e.value),t.className=e.value},E=e=>{"horizontal"===e.value&&"overlay"===t.value?(n(e),N.setAttribute("data-layout",e.value),a({value:"full",label:"Full"}),N.setAttribute("data-sidebar-style","full")):(n(e),N.setAttribute("data-layout",e.value))},K=e=>{"horizontal"===s.value&&"overlay"===e.value?alert("Sorry! Overlay is not possible in Horizontal layout."):(a(e),y("icon-hover"===e.value?"_i-hover":""),N.setAttribute("data-sidebar-style",e.value))},U=e=>{l(e),N.setAttribute("data-header-position",e.value)},W=e=>{N.setAttribute("data-theme-version",e.value),C(e)},H=e=>{A(e),N.setAttribute("data-container",e.value),"boxed"===e.value&&K({value:"overlay",label:"Overlay"})};return Object(r.useEffect)((()=>{const e=document.querySelector("body");e.setAttribute("data-typography","poppins"),e.setAttribute("data-theme-version","light"),e.setAttribute("data-layout","vertical"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-nav-headerbg","color_1"),e.setAttribute("data-headerbg","color_1"),e.setAttribute("data-sidebar-style","overlay"),e.setAttribute("data-sibebarbg","color_1"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-sidebar-position","fixed"),e.setAttribute("data-header-position","fixed"),e.setAttribute("data-container","wide"),e.setAttribute("direction","ltr");let t=()=>{P(window.innerWidth),F(window.innerHeight),window.innerWidth>=768&&window.innerWidth<1024?e.setAttribute("data-sidebar-style","mini"):window.innerWidth<=768?e.setAttribute("data-sidebar-style","overlay"):e.setAttribute("data-sidebar-style","full")};return t(),window.addEventListener("resize",t),()=>window.removeEventListener("resize",t)}),[]),Object(R.jsx)(bt.Provider,{value:{body:N,sideBarOption:[{value:"compact",label:"Compact"},{value:"full",label:"Full"},{value:"mini",label:"Mini"},{value:"modern",label:"Modern"},{value:"overlay",label:"Overlay"},{value:"icon-hover",label:"Icon-hover"}],layoutOption:[{value:"vertical",label:"Vertical"},{value:"horizontal",label:"Horizontal"}],backgroundOption:[{value:"light",label:"Light"},{value:"dark",label:"Dark"}],sidebarposition:c,headerPositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],containerPosition:[{value:"wide-boxed",label:"Wide Boxed"},{value:"boxed",label:"Boxed"},{value:"wide",label:"Wide"}],directionPosition:[{value:"ltr",label:"LTR"},{value:"rtl",label:"RTL"}],fontFamily:[{value:"poppins",label:"Poppins"},{value:"roboto",label:"Roboto"},{value:"cairo",label:"Cairo"},{value:"opensans",label:"Open Sans"},{value:"HelveticaNeue",label:"HelveticaNeue"}],primaryColor:b,navigationHader:f,windowWidth:B,windowHeight:z,changePrimaryColor:I,changeNavigationHader:T,changeSideBarStyle:K,sideBarStyle:t,changeSideBarPostion:L,sidebarpositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],changeHeaderPostion:U,headerposition:o,changeSideBarLayout:E,sidebarLayout:s,changeDirectionLayout:M,changeContainerPosition:H,direction:d,colors:["color_1","color_2","color_3","color_4","color_5","color_6","color_7","color_8","color_9","color_10","color_11","color_12","color_13","color_14","color_15"],haderColor:h,chnageHaderColor:_,chnageSidebarColor:D,sidebarColor:g,iconHover:m,menuToggle:v,openMenuToggle:()=>{"overly"===t.value?w(!0):w(!1)},changeBackground:W,background:S,containerPosition_:k,setDemoTheme:(e,t)=>{var a={},r=jt[e];N.setAttribute("data-typography",r.typography),a.value=r.version,W(a),a.value=r.layout,E(a),I(r.primary),T(r.navheaderBg),_(r.headerBg),a.value=r.sidebarStyle,K(a),D(r.sidebarBg),a.value=r.sidebarPosition,L(a),a.value=r.headerPosition,U(a),a.value=r.containerLayout,H(a),a.value=t,M(a)}},children:e.children})};o.a.render(Object(R.jsx)(c.a.StrictMode,{children:Object(R.jsx)(nt.a,{children:Object(R.jsx)(lt.a,{basename:"/",children:Object(R.jsx)(ut,{children:Object(R.jsx)(ot,{})})})})}),document.getElementById("root")),st()}},[[651,1,2]]]);
//# sourceMappingURL=main.230f1b59.chunk.js.map
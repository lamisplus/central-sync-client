import React, {forwardRef, useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import axios from "axios";
import { token as token,  url as baseUrl } from "./../../../api";
import {Modal, ModalBody, ModalHeader, Form,
    Row,Col, Card,CardBody, FormGroup, Label, Input} from 'reactstrap';
import MaterialTable from 'material-table';
import CloudUpload from '@material-ui/icons/CloudUpload';
import MatButton from '@material-ui/core/Button'
import CloudDownloadIcon from '@material-ui/icons/CloudDownload';
import {FiUploadCloud} from "react-icons/fi";
import FileSaver from "file-saver";
import 'semantic-ui-css/semantic.min.css';
import { Dropdown,Button as Buuton2, Menu, Icon } from 'semantic-ui-react'
import { ToastContainer, toast } from "react-toastify";
import ProgressBar from 'react-bootstrap/ProgressBar';
import CancelIcon from '@material-ui/icons/Cancel'
import { Spinner } from 'reactstrap';
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';

import AddBox from '@material-ui/icons/AddBox';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import Button from '@material-ui/core/Button';
import moment from "moment";

const tableIcons = {
    Add: forwardRef((props, ref) => <AddBox {...props} ref={ref}/>),
    Check: forwardRef((props, ref) => <Check {...props} ref={ref}/>),
    Clear: forwardRef((props, ref) => <Clear {...props} ref={ref}/>),
    Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref}/>),
    DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref}/>),
    Edit: forwardRef((props, ref) => <Edit {...props} ref={ref}/>),
    Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref}/>),
    Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref}/>),
    FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref}/>),
    LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref}/>),
    NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref}/>),
    PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref}/>),
    ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref}/>),
    Search: forwardRef((props, ref) => <Search {...props} ref={ref}/>),
    SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref}/>),
    ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref}/>),
    ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref}/>)
};

const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
        '& > * + *': {
            marginTop: theme.spacing(2),
        },
    },
}));


export default function DownloadNdr(props) {
    const classes = useStyles();
    const [syncList, setSyncList] = useState( [])
    const [facilities, setFacilities] = useState( [])
    const [serverUrl, setServerUrl] = useState( [])
    const [loading, setLoading] = useState('')
    const [modal, setModal] = useState(false);
    const [modal2, setModal2] = useState(false);
    const [saving, setSaving] = useState(false);
    const [disabledTables, setDisabledTables] = useState(true);
    const [errors, setErrors] = useState({});
    const [tableList, setTableList] = useState( ["patient",
            "patient_visit",
            "triage_vital_sign",
            "hiv_enrollment",
            "hiv_art_clinical",
            "hiv_art_pharmacy",
            "laboratory_order",
            "laboratory_test",
            "laboratory_sample",
            "laboratory_result",
            "biometric",
            "hiv_status_tracker",
            "hiv_eac",
            "hiv_eac_session",
            //hiv_eac_out_come,
            "hiv_observation",
            "prep_eligibility",
            "prep_enrollment",
            "prep_clinic",
            "prep_interruption"])
    const defaultValues = { facilityId: "", serverUrl : "", allTables:null, tables:"" }
    const [uploadDetails, setUploadDetails] = useState(defaultValues);
    const toggle = () => setModal(!modal);
    const toggle2 = () => setModal2(!modal2);
    useEffect(() => {
        syncHistory()
        Facilities()
        ServerUrl()
        const timer = setTimeout(() => console.log('Initial timeout!'), 1);
        return () => clearTimeout(timer);
      }, []);

           ///GET LIST OF Sync History
    async function syncHistory() {

        axios
            .get(`${baseUrl}sync/sync-history`,
           { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                setSyncList(response.data);
            })
            .catch((error) => {

            });
    
    }
    ///GET LIST OF Facilities
    async function Facilities() {
        axios
            .get(`${baseUrl}sync/facilities`,
            { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                
                setFacilities(
                    Object.entries(response.data).map(([key, value]) => ({
                        label: value.name,
                        value: value.id,
                      }))
                );
            })
            .catch((error) => {

            });
    
    }
    ///GET LIST OF Facilities
    async function ServerUrl() {
        axios
            .get(`${baseUrl}sync/remote-urls`,
             { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                
                setServerUrl(
                    Object.entries(response.data).map(([key, value]) => ({
                        label: value.url + " - " + value.username,
                        value: value.url,
                      }))
                );
            })
            .catch((error) => {

            });
    
    }
    ///GET LIST OF Tables
    async function TableList(facilityId) {
        axios
            .get(`${baseUrl}sync/table/${facilityId}`,
             { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                
                setTableList(
                    Object.entries(response.data).map((row) => ({
                        label: row,
                        value: row,
                      }))
                );
            })
            .catch((error) => {

            });
    
    }
    const downloadFile = (fileName) => {
 
        axios
            .get(`${baseUrl}ndr/download/${fileName}`,
                {headers: {"Authorization": `Bearer ${token}`}, responseType: 'blob'}
            )
            .then((response) => {
                const responseData = response.data
                let blob = new Blob([responseData], {type: "application/octet-stream"});
                FileSaver.saveAs(blob, `${fileName}.zip`);
            })
            .catch((error) => {
            });
    }
    const handleInputChange = e => {
        setUploadDetails ({...uploadDetails,  [e.target.name]: e.target.value});
        if(e.target.name ==="facilityId" ){
            TableList(e.target.value)
        }
    }
    const handleInputChangeAll =e =>{
        if(e.target.checked){ 
            setUploadDetails({...uploadDetails, [e.target.name]: e.target.checked})
            setDisabledTables(true)
         }else{
            setDisabledTables(false)
         }
 
    }
         /*****  Validation */
    const validate = () => {
        let temp = { ...errors };
        temp.facilityId = uploadDetails.facilityId
            ? ""
            : "Facility is required";
            temp.serverUrl  = uploadDetails.serverUrl 
            ? ""
            : "URL is required";
            
            setErrors({
                ...temp,
            });
            return Object.values(temp).every((x) => x === "");
    };
     const handleSubmit = (e) => {
       
        e.preventDefault();
             
            
           if(validate()){
            setSaving(true);
            const defaultTable = uploadDetails.tables!==null ? uploadDetails.tables : null ;
            axios.post(`${baseUrl}sync/upload/${defaultTable}`, uploadDetails,
            { headers: {"Authorization" : `Bearer ${token}`}},
            )
                        .then(response => {
                            setSaving(false);
                            toast.success("Upload Successful");
                            syncHistory();
                            //Closing of the modal 
                            toggle();
                        })
                        .catch(error => {
                            setSaving(false);
                            toast.error("Something went wrong");
                        });
             }       
    };
    const varient =(value)=>{
        console.log(value)
        if(value<=20 ){
            return "danger"
        }else if(value> 20 && value<=69){
            return "warning"
        }else if(value>= 70 && value<=99){
            return "info"
        }else if(value=== 100){
            return "success"
        }else{
            return "success" 
        }
    }
    const CentralUploadStatus =(value)=>{
        if(value<=0 ){
            return "Not Yet Uploaded"
        }else if(value> 0 && value<=99){
            return "Processing"
        }else if(value=== 100){
            return "Completed"
        }else{
            return "Pending" 
        }
    }
    let tableListObj=[
        {
            name:"Kwara GH",
            number:"234,900",
            fileName:"Patient",
            date:"2023-04-23",
            status:100,
            centralUpload:100
        },
        {
            name:"Kwara GH",
            number:"234,900",
            fileName:"Triage",
            date:"2023-04-23",
            status:30,
            centralUpload:30
        },
        {
            name:"Kwara GH",
            number:"234,900",
            fileName:"Hiv Enrollment",
            date:"2023-04-23",
            status:97,
            centralUpload:97
        },
        {
            name:"Kwara GH",
            number:"234,900",
            fileName:"HIV Clinic Visit",
            date:"2023-04-23",
            status:40,
            centralUpload:40
        },
        {
            name:"Kwara GH",
            number:"234,900",
            fileName:"Biomteric",
            date:"2023-04-23",
            status:0,
            centralUpload:0
        },
        {
            name:"Kwara GH",
            number:"234,900",
            fileName:"Laboratory",
            date:"2023-04-23",
            status:70,
            centralUpload:70
        },
        {
            name:"Kwara GH",
            number:"234,900",
            fileName:"Pharmacy",
            date:"2023-04-23",
            status:80,
            centralUpload:80
        },
    ];
    const syncDataBase =()=> {        
        setModal2(!modal2)
    }

    return (
        <div>
            <ToastContainer autoClose={3000} hideProgressBar />
            <Button
                variant="contained"
                color="primary"
                className=" float-right"
                startIcon={<FiUploadCloud size="10"/>}
                style={{backgroundColor: '#014d88'}}
                onClick={syncDataBase}
                //href="https://ndr.phis3project.org.ng/Identity/Account/Login?ReturnUrl=%2F"
                //onClick={loadNdrWeb}
                target="_blank"
            >
                <span> Genrate Tables</span>
            </Button>

            <br/><br/>
            <MaterialTable
                icons={tableIcons}
                title="List of Tables Generated"

                columns={[
                    {title: "Facility Name", field: "name", filtering: false},
                    {
                        title: "Number of Files Generated",
                        field: "number",
                        filtering: false
                    },
                    {title: "File Name", field: "fileName", filtering: false},
                    {title: "Date Last Generated", field: "date", type: "date", filtering: false},
                    {title: "Central Upload Status", field: "status", filtering: false},

                    {
                        title: "Action",
                        field: "actions",
                        filtering: false,
                    },
                ]}
                isLoading={loading}
                data={tableListObj.map((row) => ({
                    name: row.name,
                    number: row.number,
                    fileName: row.fileName,
                    date: moment(row.date).format("LLLL"),
                    status:(
                            <>
                            <p><b>{CentralUploadStatus(row.centralUpload)}</b></p>

                            <ProgressBar now={row.status} variant={varient(row.status)} label={`${row.status}%`} />
                            </>  
                            ),
                    actions:(<div>
                    <Menu.Menu position='right'  >
                    <Menu.Item >
                        <Buuton2 style={{backgroundColor:'rgb(153,46,98)'}} primary>
                        <Dropdown item text='Action'>

                        <Dropdown.Menu style={{ marginTop:"10px", }}>
                            <Dropdown.Item  onClick={() => downloadFile(row.fileName)} title="Download"><CloudDownloadIcon color="primary"/> Download
                            </Dropdown.Item>
                            <Dropdown.Item  ><CloudUpload color="primary" title="Upload to Central"/> Upload To Central
                            </Dropdown.Item>
                            
                        </Dropdown.Menu>
                    </Dropdown>
                        </Buuton2>
                    </Menu.Item>
                    </Menu.Menu>
              </div>)
                }))}
                options={{

                    pageSizeOptions: [5, 10, 50, 100, 150, 500],
                    headerStyle: {
                        backgroundColor: "#014d88",
                        color: "#fff",
                        margin: "auto"
                    },
                    filtering: true,
                    searchFieldStyle: {
                        width: '300%',
                        margingLeft: '250px',
                    },
                    exportButton: false,
                    searchFieldAlignment: 'left',
                }}

            />

        {/* <Modal isOpen={modal} toggle={toggle} backdrop={false} fade={true} style={{marginTop:"250px"}} size='lg'>
        
        <ModalBody>
        <h1>Uploading File To NDR. Please wait...</h1>
        </ModalBody>
        
        </Modal>  */}
        <Modal isOpen={modal2} toggle={toggle2} className={props.className} size="lg"  backdrop="static">
        <Form >
        <ModalHeader toggle={toggle2}>Upload</ModalHeader>
            <ModalBody>   
                <Card >
                    <CardBody>
                        <Row >                                  
                        <Col md={12}>
                        <FormGroup>
                        <Label >Facility *</Label>
                            <Input
                                type="select"
                                name="facilityId"
                                id="facilityId"
                                onChange={handleInputChange}
                                style={{border: "1px solid #014D88",borderRadius:"0.2rem"}}
                                required
                                >
                                <option > </option>
                                {facilities.map(({ label, value }) => (
                                    <option key={value} value={value}>
                                    {label}
                                    </option>
                                ))}
                            </Input>
                            {errors.facilityId !=="" ? (
                                <span className={classes.error}>{errors.facilityId}</span>
                            ) : "" } 
                        </FormGroup>
                        </Col> 
                        <Col md={12}>
                        <FormGroup>
                        <Label >URL *</Label>
                                <Input
                                    type="select"
                                    name="serverUrl"
                                    id="serverUrl"
                                    onChange={handleInputChange}
                                    style={{border: "1px solid #014D88",borderRadius:"0.2rem"}}
                                    required
                                    >
                                    {serverUrl.map(({ label, value }) => (
                                        <option key={value} value={value}>
                                        {label}
                                        </option>
                                    ))}
                                </Input>
                                {errors.serverUrl !=="" ? (
                                    <span className={classes.error}>{errors.serverUrl}</span>
                                ) : "" } 
                        </FormGroup>
                        </Col>
                        {!disabledTables && (
                            <>
                                <Col md={12}>
                                <FormGroup>
                                <Label >Tables</Label>
                                        <Input
                                            type="select"
                                            name="tables"
                                            id="tables"
                                            onChange={handleInputChange}
                                            style={{border: "1px solid #014D88",borderRadius:"0.2rem"}}
                                        // disabled={}
                                        >
                                            <option >Select Table </option>
                                            {tableList.map((row) => (
                                                <option key={row} value={row}>
                                                {row}
                                                </option>
                                            ))}
                                        </Input>
                                        {errors.tables !=="" ? (
                                            <span className={classes.error}>{errors.tables}</span>
                                        ) : "" } 
                                </FormGroup>
                                </Col>
                            </>
                        )} 
                        <div className="form-group mb-3 col-md-12">                                    
                        <div className="form-check custom-checkbox ml-1 ">
                            <input
                            type="checkbox"
                            className="form-check-input"                           
                            name="allTables"
                            id="allTables"
                            checked={disabledTables}
                            onChange={handleInputChangeAll}
                            />
                            <label
                            className="form-check-label"
                            htmlFor="basic_checkbox_1"
                            >
                                All Tables
                            </label>
                        </div>
                        </div>
                        </Row>
                        <br/>
                        {saving ? <Spinner /> : ""}
                        <br />
                        
                        <MatButton
                            type='submit'
                            variant='contained'
                            color='primary'
                            className={classes.button}
                            style={{backgroundColor:'#014d88',fontWeight:"bolder"}}
                            startIcon={<SettingsBackupRestoreIcon />}
                            onClick={handleSubmit}
                            
                        >   
                            {!saving ? (
                            <span style={{ textTransform: "capitalize" }}>Upload</span>
                            ) : (
                            <span style={{ textTransform: "capitalize" }}>Uploading Please Wait...</span>
                            )
                        } 
                        </MatButton>                                          
                        <MatButton
                            variant='contained'
                            color='default'
                            onClick={toggle2}
                            className={classes.button}
                            style={{backgroundColor:'#992E62'}}
                            startIcon={<CancelIcon />}
                        >
                            <span style={{ textTransform: "capitalize ", color:"#fff" }}>cancel</span>
                        </MatButton>
                    </CardBody>
                </Card> 
            </ModalBody>
        </Form>
    </Modal>
        </div>

    );

}

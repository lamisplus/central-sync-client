import React, { useState, useEffect } from 'react'
import MaterialTable from 'material-table';
import axios from "axios";
import { token as token,  url as baseUrl } from "./../../../api";
import { forwardRef } from 'react';
import Progress from './Progress';
import CloudUpload from '@material-ui/icons/CloudUpload';
import moment from "moment";
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

import Button from "@material-ui/core/Button";
import {  toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
//import { Menu, MenuList, MenuButton, MenuItem } from "@reach/menu-button";
import "@reach/menu-button/styles.css";
//import { MdModeEdit } from "react-icons/md";
import 'react-widgets/dist/css/react-widgets.css';
import { Modal, ModalHeader, ModalBody,Form,FormFeedback,
    Row,Col, Card,CardBody, FormGroup, Label, Input, Badge} from 'reactstrap';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
//import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import CloudDownloadIcon from '@material-ui/icons/CloudDownload';
import VisibilityIcon from '@mui/icons-material/Visibility';
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';
import FileSaver from "file-saver";
import 'semantic-ui-css/semantic.min.css';
import { Dropdown,Button as Buuton2, Menu,  } from 'semantic-ui-react'
import Logs from "./Logs";
import SendToServer from "./SendToServer";
import Generatekey from "./Generatekey";
import ProgressBar from "react-bootstrap/ProgressBar";
import GeneratedFilesList from './GeneratedFilesList';
import { Box, Typography } from '@material-ui/core';
import { Varient } from '../Utils/UtilFunctions';


const tableIcons = {
Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};

const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    },
    cardBottom: {
        marginBottom: 20
    },
    Select: {
        height: 45,
        width: 350
    },
    button: {
        margin: theme.spacing(1)
    },

    root: {
        '& > *': {
            margin: theme.spacing(1)
        }
    },
    input: {
        display: 'none'
    },
    error: {
        color: "#f85032",
        fontSize: "11px",
    },
    success: {
        color: "#4BB543 ",
        fontSize: "11px",
    }, 
}))

const SyncList = (props) => {
  //let history = useHistory();
  // The state for our timer
  const classes = useStyles()
  const [syncList, setSyncList] = useState( [])
  const [facilities, setFacilities] = useState( [])
  const [modal, setModal] = useState(false);
  const [generateFilesGrid, setGenerateFilesGrid] = useState(false);
  const [generateKeyModal, setGenerateKeyModal] = useState(false);
  const [genKey, setGenKey] = useState("");
  const [sendToServerModal, setSendToServerModal] = useState(false);
  const [logModal, setLogModal] = useState(false);
  const toggle = () => setModal(!modal);
  const toggleLogModal = () => setLogModal(!logModal);
  const toggleGenerateFilesGrid = () => setGenerateFilesGrid(!generateFilesGrid);
  const toggleGenerateKeyModal = () => setGenerateKeyModal(!generateKeyModal);
  const toggleSendToServerModal = () => setSendToServerModal(!sendToServerModal);
  const [modal2, setModal2] = useState(false);
  const toggle2 = () => setModal2(!modal2);
  const defaultValues = { facilityId: "", startDate : "", endDate:"", all:false}
  const [uploadDetails, setUploadDetails] = useState(defaultValues);
  const [saving, setSaving] = useState(false);
  const [errors, setErrors] = useState({});
  const [uploadPercentage, setUploadPercentage] = useState(0);
  const [showErrorTable, setShowErrorTable] = useState(false);
  const [showErrorObj, setShowErrorObj] = useState([]);
  const [showErrorFileObj, setShowErrorFileObj] = useState();
  const [rowObj, setRowObj] = useState(null);
  const [syncHistoryId, setSyncHistoryId] = useState();
  const [syncHistoryTrackerUuid, setSyncHistoryTrackerUuid] = useState();

useEffect(() => {
    Facilities();
    JsonSyncHistory();
    }, []);

    // const varient = (value) => {
    //     // console.log(value);
    //     if (value <= 20) {
    //       return "danger";
    //     } else if (value > 20 && value <= 69) {
    //       return "warning";
    //     } else if (value >= 70 && value <= 99) {
    //       return "info";
    //     } else if (value === 100) {
    //       return "success";
    //     } else {
    //       return "success";
    //     }
    //   };

    /*****  Validation */
    const validate = () => {
        let temp = { ...errors };
        temp.facilityId = uploadDetails.facilityId
            ? ""
            : "Facility is required";
            setErrors({
                ...temp,
            });
            return Object.values(temp).every((x) => x === "");
    };
     ///GET LIST OF Sync History
    async function JsonSyncHistory() {
        axios
            .get(`${baseUrl}export/sync-histories`,
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
            .get(`${baseUrl}account`,
            { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                
                setFacilities(
                    Object.entries(response.data.applicationUserOrganisationUnits).map(([key, value]) => ({
                        label: value.organisationUnitName,
                        value: value.organisationUnitId,
                      }))
                );
            })
            .catch((error) => {

            });
    
    }
    const handleInputChange = e => {
        setUploadDetails ({...uploadDetails,  [e.target.name]: e.target.value});
    }
    const handleCheckBox =e =>{
        if(e.target.checked){
            setUploadDetails ({...uploadDetails,  ['all']: e.target.checked});  
            //setOvcEnrolled(true)
        }else{
            setUploadDetails ({...uploadDetails,  ['all']: false}); 
        }
    }
    const handleSubmit = async e => {  
        e.preventDefault();
        setSaving(true);
        if(validate()){
            try {
                const res = await axios.get(`${baseUrl}export/all?facilityId=${uploadDetails.facilityId}&current=${uploadDetails.all}`, {
                    headers: {"Authorization" : `Bearer ${token}`},
                    onUploadProgress: progressEvent => {
                        setUploadPercentage(
                        parseInt(
                            Math.round((progressEvent.loaded * 100) / progressEvent.total)
                        )
                        );
                        // Clear percentage
                        setTimeout(() => setUploadPercentage(0), 10000);
                    }
                });
                toast.success("JSON Extraction was successful!");
                toggle();
                JsonSyncHistory();
                setSaving(false);
            } catch (err) {
                setSaving(false); 
                }  
        }else{
            toast.error("Please select facility");
        }    
       
    };

    const fetchGeneratedFiles = (id) => {

    }

    const generateJsonFile =()=> {        
        setModal(!modal)
    }
    const displayGeneratedfiles = (rowObj) => {
        setRowObj(rowObj)
        setSyncHistoryId(rowObj.id);
        setGenerateFilesGrid(true);
    }
    const displayGenerateKey =(row)=> {
        setGenKey(row.genKey);
        setGenerateKeyModal(!generateKeyModal);
    }
    const displayLogs = (row) => {
        setRowObj(row)
        setSendToServerModal(false)
        setGenerateFilesGrid(false)
        setShowErrorTable(true)
    }
    const sendToServerAction =(rowObj)=> {
        setSendToServerModal(!sendToServerModal);
        setRowObj({...rowObj, syncHistoryTrackerUuid: null})
    }
    const displaySendToServer =()=> {
        setSendToServerModal(!sendToServerModal)
    }
    const downloadFile = (fileName) => {
        axios
            .get(`${baseUrl}export/download/${fileName}`,
                {headers: {"Authorization": `Bearer ${token}`}, responseType: 'blob'}
            )
            .then((response) => {
                const responseData = response.data
                let blob = new Blob([responseData], {type: "application/octet-stream"});
                FileSaver.saveAs(blob, `${fileName}`);
            })
            .catch((error) => {
            });
    }

    const logStats = (messageLogs) => {
        return [
            messageLogs.filter((log) => log.category.toLowerCase() === 'success').length,
            messageLogs.filter((log) => log.category.toLowerCase() === 'warning').length,
            messageLogs.filter((log) => log.category.toLowerCase() === 'error').length,
        ]
    }

    const canExportFiles = () => {
        // map through the generated files and check if any of them hasError
        let canExport = true;
        syncList.forEach((file) => {
            if(file.hasError){
                canExport = false;
            }
        });
        
    }


 
  return (
    <>
    { !generateFilesGrid ? (<div>
            {!showErrorTable ? (<>
                <Button
                variant="contained"
                style={{backgroundColor:"#014d88", }}
                className=" float-right mr-1"
                //startIcon={<FaUserPlus />}
                onClick={generateJsonFile}
                >
                <span style={{ textTransform: "capitalize", color:"#fff" }}>Generate JSON Files </span>
                </Button>       
            <br/><br/>
            <MaterialTable
            icons={tableIcons}
                title="Generated JSON Files List "
                columns={[
                // { title: " ID", field: "Id" },
                {
                    title: "Facility Name",
                    field: "facilityName",
                },
                { title: "File Name ", field: "tableName", filtering: false },
                { title: "Size (MB)", field: "uploadSize", filtering: false },
                { title: "Upload (%) ", field: "uploadPercentage", filtering: false },
                { title: "Date Generated ", field: "date", filtering: false },
                { title: "Status", field: "status", filtering: false },         
                { title: "Action", field: "actions", filtering: false }, 
                ]}
                data={ syncList.map((row) => ({
                    //Id: manager.id,
                    facilityName: row.facilityName,
                    tableName: row.tableName,
                    uploadSize: (<>{(row.uploadSize / 1000000).toFixed(2) + ' MB'}</>),
                    uploadPercentage: (<div style={{ width: "100%", height: "100%", display:"flex", flexDirection: "column", alignItems:"center", justifyContent: "flex-start"}}>
                        <ProgressBar
                            now={row.percentageSynced}
                            variant={Varient(row.percentageSynced)}
                            style={{width:"100%"}}
                            // label={`${row.percentageSynced}%`}
                        />
                        <Typography>{`${row.percentageSynced.toFixed(0)}%`}</Typography>
                    </div>),
                    date:  moment(row.dateLastSync).format("LLLL"),
                    // status: row.messageLog===null ? row.processed===0 ? "Processing" : "Completed" : "Error",
                    status: (<Box style={{width:"100%", height:"auto"}}>
                        <Typography>{"Completed"}</Typography>
                        <div style={{display:"flex", flexDirection:"row", justifyContent:"flex-start", marginTop: "10px"}}>
                            <Badge color="success">{logStats(row.messageLog)[0]}</Badge>
                            <Badge style={{marginLeft:"5px"}} color="warning">{logStats(row.messageLog)[1]}</Badge>
                            <Badge style={{marginLeft:"5px"}} color="danger">{logStats(row.messageLog)[2]}</Badge>
                        </div>
                    </Box>),
                    //errorLog: row.errorLog,
                    actions:(<div>
                                <Menu.Menu position='right'  >
                                <Menu.Item >
                                    <Buuton2 style={{backgroundColor:'rgb(153,46,98)'}} primary>
                                    <Dropdown item text='Action'>

                                    <Dropdown.Menu style={{ marginTop:"10px", }}>

                                            <Dropdown.Item disabled={(row.hasError !== null && row.hasError) || Math.ceil(row.percentageSynced) === 100}  onClick={() => downloadFile(row.tableName)}><CloudDownloadIcon color="primary"/> Download File
                                            </Dropdown.Item>
                                            <Dropdown.Item disabled={(row.hasError !== null && row.hasError) || Math.ceil(row.percentageSynced) === 100}  onClick={() => sendToServerAction(row)}><CloudUpload color="primary"/> Send To Server
                                            </Dropdown.Item>
                                            {/* <Dropdown.Item onClick={() => sendToServerAction(row)}><CloudUpload color="primary"/> Send To Server
                                            </Dropdown.Item> */}
                                            <Dropdown.Item  onClick={() => displayGeneratedfiles(row)}><VisibilityIcon color="primary"/>View Generated Files
                                            </Dropdown.Item>
                                            <Dropdown.Item  onClick={() => displayGenerateKey(row)}><VisibilityIcon color="primary"/>View Generated Key
                                            </Dropdown.Item>
                                            <Dropdown.Item  onClick={() => displayLogs(row)}><VisibilityIcon color="primary"/>View Logs
                                            </Dropdown.Item>

                                    </Dropdown.Menu>
                                </Dropdown>
                                    </Buuton2>
                                </Menu.Item>
                                </Menu.Menu>
                            </div>)
                    }))}
            
                    options={{
                            headerStyle: {
                                backgroundColor: "#014d88",
                                color: "#fff",
                            },
                            searchFieldStyle: {
                                width : '200%',
                                margingLeft: '250px',
                            },
                            filtering: false,
                            exportButton: canExportFiles(),
                            searchFieldAlignment: 'left',
                            pageSizeOptions:[10,20,100],
                            pageSize:10,
                            debounceInterval: 400
                    }}
            />
            </>) : (
            <>
            <Logs setShowErrorTable={setShowErrorTable} rowObj={rowObj}  />
            </>
            )}

            <Modal isOpen={modal} toggle={toggle} className={props.className} size="lg"  backdrop="static">
                <Form >
                <ModalHeader toggle={toggle}>Generate JSON Files</ModalHeader>
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
                                        vaulue={uploadDetails.facilityId}
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
                                <div className="form-check custom-checkbox ml-3 ">
                                    <input
                                    type="checkbox"
                                    className="form-check-input"
                                    name="all"
                                    id="all"                                        
                                    onChange={handleCheckBox}
                                    checked={uploadDetails.all}
                                    //disabled={locationState.actionType==='update'? false : true}
                                    />
                                    <label
                                    className="form-check-label"
                                    htmlFor="all"
                                    >
                                    Recent Update ?
                                    </label>
                                </div>
                                </Row>
                                <br/>
                                <b>{uploadDetails.all===true ? "Only the updated records will be pushed" : "You are generating records from initial"}</b>
                                <br/>
                                {saving ?
                                <Progress percentage={uploadPercentage} /> 
                                : ""}
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
                                    <span style={{ textTransform: "capitalize" }}>Generate</span>
                                    ) : (
                                    <span style={{ textTransform: "capitalize" }}>Generating Please Wait...</span>
                                    )
                                } 
                                </MatButton>                                          
                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={toggle}
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
            <Modal isOpen={modal2} toggle={toggle2} backdrop={false} fade={true} style={{marginTop:"250px"}} size='lg'>            
                <ModalBody>
                    <h1>Uploading File To Server. Please wait...</h1>
                </ModalBody>
            </Modal>

            <SendToServer refreshPrevious={JsonSyncHistory} toggleModal={toggleSendToServerModal} showModal={sendToServerModal}  rowObj={rowObj}/>
            <Generatekey toggleModal={toggleGenerateKeyModal} showModal={generateKeyModal} genKey={genKey}  />
        </div>
        ) : (
            <GeneratedFilesList jsonSyncHistory={JsonSyncHistory} setGenerateFilesGrid={setGenerateFilesGrid} id={syncHistoryId} />
        )}
    </>
  );
}

export default SyncList;



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
    Row,Col, Card,CardBody, FormGroup, Label, Input} from 'reactstrap';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
//import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import CloudDownloadIcon from '@material-ui/icons/CloudDownload';
import { Spinner } from 'reactstrap';
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';
import FileSaver from "file-saver";
import 'semantic-ui-css/semantic.min.css';
import { Dropdown,Button as Buuton2, Menu, Icon } from 'semantic-ui-react'

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
  const toggle = () => setModal(!modal);
  const [modal2, setModal2] = useState(false);
  const toggle2 = () => setModal2(!modal2);
  const defaultValues = { facilityId: "", startDate : "", endDate:"",}
  const [uploadDetails, setUploadDetails] = useState(defaultValues);
  const [saving, setSaving] = useState(false);
  const [errors, setErrors] = useState({});
  const [uploadPercentage, setUploadPercentage] = useState(0);

useEffect(() => {
    syncHistory();
    Facilities();
    JsonSyncHistory();
    }, []);
    
// useEffect(() => {
//     const interval = setInterval(() => {
//         syncHistory()
//     }, 5000);
//     return () => clearInterval(interval);
// }, []);
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
    async function syncHistory() {

        axios
            .get(`${baseUrl}account`,
           { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                setSyncList(response.data.applicationUserOrganisationUnits);
            })
            .catch((error) => {

            });
    
    }
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
    const handleSubmit = async e => {  
        e.preventDefault();
        setSaving(true);
        if(validate()){
            try {
                const res = await axios.get(`${baseUrl}export/all?facilityId=${uploadDetails.facilityId}`, {
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
            } catch (err) {
                // console.log(err) 
                }  
        }else{
            toast.error("All Fields are required");
        }    
       
    };
    const generateJsonFile =()=> {        
        setModal(!modal)
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
    const  sendToServerAction = (fileName,facilityId) => {
        setModal2(!modal2)
         //SENDING A POST REQUEST 
         axios.post(`${baseUrl}export/send-data?fileName=${fileName}&facilityId=${facilityId}`,
                     { headers: {"Authorization" : `Bearer ${token}`} }
                   )
            .then(response => {
            window.setTimeout(() => {
                toast.success(" Uploading To server Successful!");
                toggle2();
                JsonSyncHistory()
            }, 1000);
            })
            .catch(error => {
             setModal(false)
            //toast.error(" Something went wrong!");
            if(error.response && error.response.data){
                let errorMessage = error.response.data.apierror && error.response.data.apierror.message!=="" ? error.response.data.apierror.message :  "Something went wrong, please try again";
                toast.error(errorMessage);
                setModal(false)
            }
            else{
                toggle2();
                toast.error("Something went wrong. Please try again...");
            }
        });

    }
 
  return (
    <div>
       
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
            { title: "Upload Size ", field: "uploadSize", filtering: false },
            { title: "Date Generated ", field: "date", filtering: false },
            { title: "Status", field: "status", filtering: false },        
            { title: "Action", field: "actions", filtering: false }, 
            ]}
            data={ syncList.map((row) => ({
                //Id: manager.id,
                facilityName: row.facilityName,
                tableName: row.tableName,
                url: row.tableName,
                uploadSize: row.uploadSize,
                date:  moment(row.dateLastSync).format("LLLL"),
                status: row.processed===0 ? "Processing" : "Completed",
                actions:(<div>
                    <Menu.Menu position='right'  >
                    <Menu.Item >
                        <Buuton2 style={{backgroundColor:'rgb(153,46,98)'}} primary>
                        <Dropdown item text='Action'>

                        <Dropdown.Menu style={{ marginTop:"10px", }}>
                            <Dropdown.Item  onClick={() => downloadFile(row.tableName)}><CloudDownloadIcon color="primary"/> Download File
                            </Dropdown.Item>
                            <Dropdown.Item  onClick={() => sendToServerAction(row.tableName, row.organisationUnitId)}><CloudUpload color="primary"/> Send To Server
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
                        exportButton: false,
                        searchFieldAlignment: 'left',
                        pageSizeOptions:[10,20,100],
                        pageSize:10,
                        debounceInterval: 400
         }}
        />
    
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
                        </Row>
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
    </div>
  );
}

export default SyncList;


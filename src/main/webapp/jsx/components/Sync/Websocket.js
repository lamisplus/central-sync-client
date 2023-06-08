import React, { useState, useRef, useCallback, useEffect } from 'react'
//import MaterialTable from 'material-table';
import axios from "axios";
import { token as token,  url as baseUrl } from "../../../api";
import { forwardRef } from 'react';
//import { Link } from 'react-router-dom'
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
import { Form,
    Row,Col, Card,CardBody, FormGroup, Label, Input} from 'reactstrap';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
//import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { Spinner } from 'reactstrap';
//import { DropzoneArea } from 'material-ui-dropzone';
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';
import { useHistory } from "react-router-dom";
import useWebSocket, { ReadyState } from 'react-use-websocket';

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
    let history = useHistory();
  // The state for our timer
  const classes = useStyles()
  const [syncList, setSyncList] = useState( [])
  const [facilities, setFacilities] = useState( [])
  const [serverUrl, setServerUrl] = useState( [])
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);
  const defaultValues = { facilityId: "", serverUrl : "", allTables:null, tables:"" }
  const [uploadDetails, setUploadDetails] = useState(defaultValues);
  const [saving, setSaving] = useState(false);
  const [disabledTables, setDisabledTables] = useState(true);
  const [errors, setErrors] = useState({});
  const [tableList, setTableList] = useState( [])

    useEffect(() => {
        syncHistory()
        Facilities()
        ServerUrl()
        const timer = setTimeout(() => console.log('Initial timeout!'), 1);
        return () => clearTimeout(timer);
    }, []);


    //Public API that will echo messages sent to it back to the client
  const [socketUrl, setSocketUrl] = useState('wss://demo.piesocket.com/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self');
  const [messageHistory, setMessageHistory] = useState([]);

  const { sendMessage, lastMessage, readyState } = useWebSocket(socketUrl);

  useEffect(() => {
    if (lastMessage !== null) {
      setMessageHistory((prev) => prev.concat(lastMessage));
    }
  }, [lastMessage, setMessageHistory]);

  const handleClickChangeSocketUrl = useCallback(
    () => setSocketUrl('ws://demos.kaazing.com/echo'),
    []
  );

  const handleClickSendMessage = useCallback(() => sendMessage('Hello Mathew testing'), []);

  const connectionStatus = {
    [ReadyState.CONNECTING]: 'Connecting',
    [ReadyState.OPEN]: 'Open',
    [ReadyState.CLOSING]: 'Closing',
    [ReadyState.CLOSED]: 'Closed',
    [ReadyState.UNINSTANTIATED]: 'Uninstantiated',
   }[readyState];

       
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
    //GET LIST OF Sync History
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

    const syncDataBase =()=> {        
        setModal(!modal)
    }

    const apiCall = {
        event: 'bts:subscribe',
        data: { channel: 'order_book_btcusd' },
    }; 
 
    const [bids, setBids] = useState([0]);

    useEffect(() => {
        const ws = new WebSocket('wss://ws.bitstamp.net');
        ws.onopen = (event) => {
            ws.send(JSON.stringify(apiCall));
        };
        ws.onmessage = function (event) {
            const json = JSON.parse(event.data);
            try {
                if ((json.event == 'data')) {
                    setBids(json.data.bids.slice(0, 5));
                }
            } catch (err) {
                console.log(err);
            }
        };
        //clean up function
        return () => ws.close();
    }, []);
    const firstBids = bids.map((item, index) => (
        <div key={index}>
            <p> {item}</p>
        </div>
    ));
  return (
    <div>
       
        <Button
            variant="contained"
            style={{backgroundColor:"#014d88", }}
            className=" float-right mr-1"
            //startIcon={<FaUserPlus />}
            onClick={syncDataBase}
          >
            <span style={{ textTransform: "capitalize", color:"#fff" }}>Upload </span>
        </Button>        
        <br/><br/>
        <div>{firstBids}</div>
        <Button onClick={handleClickChangeSocketUrl}>
            Click Me to change Socket Url
        </Button>
        <Button
            onClick={handleClickSendMessage}
            disabled={readyState !== ReadyState.OPEN}
        >
            Click Me to send 'Hello'
        </Button>

        <span>The WebSocket is currently {connectionStatus}</span>
        {lastMessage ? <span>Last message: {lastMessage.data}</span> : null}
        <ul>
            {messageHistory.map((message, idx) => (
            <span key={idx}>{message ? message.data : null}</span>
            ))}
        </ul>
             <Form >
  
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
                            onClick={toggle}
                            className={classes.button}
                            style={{backgroundColor:'#992E62'}}
                            startIcon={<CancelIcon />}
                        >
                            <span style={{ textTransform: "capitalize ", color:"#fff" }}>cancel</span>
                        </MatButton>
                    </CardBody>
                </Card> 
          
        </Form>
   
    </div>
  );
}

export default SyncList;



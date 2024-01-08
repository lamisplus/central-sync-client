import React, { useState, useEffect } from 'react'
import MaterialTable from 'material-table';
import axios from "axios";
import { token as token,  url as baseUrl } from "./../../../api";
import { forwardRef } from 'react';
import {
    Modal, ModalHeader, ModalBody, Form, Card, CardBody, FormGroup, Label, Input, Alert
} from 'reactstrap';
import CancelIcon from '@material-ui/icons/Cancel';
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
import {Row, Col} from 'reactstrap';

import Button from "@material-ui/core/Button";
import {  toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
//import { Menu, MenuList, MenuButton, MenuItem } from "@reach/menu-button";
import "@reach/menu-button/styles.css";
//import { MdModeEdit } from "react-icons/md";
import 'react-widgets/dist/css/react-widgets.css';
import { makeStyles } from '@material-ui/core/styles'

import VisibilityIcon from '@mui/icons-material/Visibility';

import 'semantic-ui-css/semantic.min.css';
import { Dropdown,Button as Buuton2, Menu,  } from 'semantic-ui-react'
import AddAppKey from "./AddAppKey";
import { Box, Typography } from '@material-ui/core';
import DownloadButton from '../Sync/DownloadButton';
import { set } from 'date-fns';


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

const AppKeyList = (props) => {
    //let history = useHistory();
    // The state for our timer
    const classes = useStyles()
    const [keyList, setKeyList] = useState( [])
    const [appKeysListTable, showAppKeysListTable] = useState( true)
    const [keyObj, setKeyObj] = useState( null)
    const [showViewKey, setShowViewKey] = useState(false)
    const [appKeyModalIsOpen, setAppKeyModalIsOpen] = useState(false);


    const toggleAppKeyModalIsOpen = () => {
        setAppKeyModalIsOpen(!appKeyModalIsOpen);
    }
    useEffect(() => {
        AppKeyHistory();
    }, []);

    ///GET LIST OF Sync History
    async function AppKeyHistory() {
        axios
            .get(`${baseUrl}sync/app-key`,
                { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                setKeyList(response.data);
            })
            .catch((error) => {
            });
    }
    const createAppKey =()=> {
        setShowViewKey(false)
        showAppKeysListTable(false)
    }
    const updateKey = (rowObj) => {
        setShowViewKey(false)
        showAppKeysListTable(false)
        setKeyObj(rowObj)
    }
    const viewAppKey =(rowObj)=> {
        // setShowViewKey(true)
        setKeyObj(rowObj)
        setAppKeyModalIsOpen(true);
        // showAppKeysListTable(false)
    }
    //

    return (
        <div>
            {appKeysListTable ? (<>
                <Button
                    variant="contained"
                    style={{backgroundColor: keyList.length === 0 && "#014d88" }}
                    className=" float-right mr-1"
                    //startIcon={<FaUserPlus />}
                    onClick={createAppKey}
                    disabled={keyList.length > 0}
                >
                    <span style={{ textTransform: "capitalize", color:"#fff" }}>Add App Key </span>
                </Button>
                <br/><br/>
                <MaterialTable
                    icons={tableIcons}
                    title="APP Key List "
                    columns={[
                        // { title: " ID", field: "Id" },
                        {
                            title: "Facility Name",
                            field: "facilityName",
                        },
                        { title: "Server Url", field: "serverUrl", filtering: false },
                        { title: "Action", field: "actions", filtering: false },
                    ]}
                    data={ keyList.map((row) => ({
                        //Id: manager.id,
                        facilityName: row.facilityName,
                        serverUrl: row.serverUrl,
                        actions:(<div>
                            <Menu.Menu position='right'  >
                                <Menu.Item >
                                    <Buuton2 style={{backgroundColor:'rgb(153,46,98)'}} primary>
                                        <Dropdown item text='Action'>

                                            <Dropdown.Menu style={{ marginTop:"10px", }}>

                                                {row.appKey && <Dropdown.Item  onClick={() => viewAppKey(row)}><VisibilityIcon color="primary"/>View key
                                                </Dropdown.Item>}
                                                {row.appKey && <Dropdown.Item  onClick={() => updateKey(row)}><Edit color="primary"/>Edit key
                                                </Dropdown.Item>}

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
                        exportButton: true,
                        searchFieldAlignment: 'left',
                        pageSizeOptions:[10,20,100],
                        pageSize:10,
                        debounceInterval: 400
                    }}
                />
            </>)
                :
            (<>
            <AddAppKey setShowViewKey={setShowViewKey} showViewKey={showViewKey} showAppKeysListTable={showAppKeysListTable} AppKeyHistory={AppKeyHistory} keyObj={keyObj}/>
            </>)
            }

        <Modal isOpen={appKeyModalIsOpen} style={{ textTransform: "none" }} toggle={toggleAppKeyModalIsOpen} size="lg" backdrop='static'>
                <Form >
                    <ModalHeader className={classes.header} toggle={toggleAppKeyModalIsOpen}>App Key</ModalHeader>
                    <ModalBody>

                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={12} >
                                        <Alert color="primary" >
                                            <p>
                                                Info : &nbsp;&nbsp;&nbsp;<span style={{ fontWeight: 'bolder' }}>{'Kindly click on the button to download the app key'}</span>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                            </p>

                                        </Alert>

                                        <Typography marginTop={2}>
                                            {keyObj && keyObj.appKey}
                                        </Typography>

                                        <Box display={"flex"} marginTop={"10px"} justifyContent={"space-between"} width={"100%"} >
                                            <DownloadButton styles={classes.button} textToDownload={keyObj && keyObj.appKey} />

                                            <Button variant='contained' color='default' onClick={toggleAppKeyModalIsOpen} >
                                                <CancelIcon />
                                                <Typography style={{ marginLeft: "5px" }}>
                                                    Cancel
                                                </Typography>
                                            </Button>
                                        </Box>
                                    </Col>

                                </Row>

                            </CardBody>
                        </Card>
                    </ModalBody>

                </Form>
            </Modal>

        </div>
    );
}

export default AppKeyList;



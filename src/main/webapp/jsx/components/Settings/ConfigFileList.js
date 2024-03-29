import React, { useState, useRef, useEffect } from 'react'
import MaterialTable from 'material-table';
import axios from "axios";
import { token as token, url as baseUrl } from "./../../../api";
import UploadConfigFile from './UploadConfigFile';
import { forwardRef } from 'react';
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
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import 'react-widgets/dist/css/react-widgets.css';
import { MdModeEdit } from "react-icons/md";
import "@reach/menu-button/styles.css";
import { makeStyles } from '@material-ui/core/styles'
import 'semantic-ui-css/semantic.min.css';
import { Dropdown, Button as Buuton2, Menu, } from 'semantic-ui-react'
import VisibilityIcon from '@mui/icons-material/Visibility';
import moment from 'moment';

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


const ConfigFileList = (props) => {

    const [serverConfig, setServerConfig] = useState([])
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal);
    const [configDetailObj, setConfigDetailObj] = useState(null)
    const [showConfigView, setShowConfigView] = useState(false)
    useEffect(() => {
        ServerConfigFile()
    }, []);
    ///GET LIST OF Remote URL
    async function ServerConfigFile() {
        axios
            .get(`${baseUrl}sync/sync-config`,
                { headers: { "Authorization": `Bearer ${token}` } }
            )
            .then((response) => {

                setServerConfig(response.data)

            })
            .catch((error) => {

            });

    }
    const uploadConfigFile = () => {
        setShowModal(!showModal)
    }
    const viewConfigDetail = (row) => {
        setShowConfigView(true)
        setConfigDetailObj(row)
    }
    const backButton = () => {
        setShowConfigView(false)
    }


    return (
        <div>
            <Button
                variant="contained"
                color="primary"
                className=" float-right mr-1"
                style={{ backgroundColor: "#014d88" }}
                onClick={showConfigView ? backButton : uploadConfigFile}
            >
                <span style={{ textTransform: "capitalize" }}>{showConfigView ? "<< Back" : "Upload Config File"} </span>
            </Button>
            <br /><br />
            <br />
            {!showConfigView && (
                <MaterialTable
                    icons={tableIcons}
                    title="Config Information "
                    columns={[
                        { title: " File Name", field: "name" },
                        {
                            title: "Version",
                            field: "version",
                        },
                        { title: "Release Date", field: "releaseDate", filtering: false },
                        { title: "Upload Date", field: "uploadDate", filtering: false },
                        { title: "Status", field: "status", filtering: false },
                        { title: "Action", field: "actions", filtering: false },


                    ]}
                    data={serverConfig.sort((a, b) =>
                        moment(a.uploadDate).isBefore(moment(b.uploadDate)) ? 1 : -1
                    ).map((row) => ({
                        //Id: manager.id,
                        name: row.name,
                        version: row.version,
                        releaseDate: moment(row.releaseDate).format("LLLL"),
                        uploadDate: moment(row.uploadDate).format("LLLL"),
                        status: row.active === true ? "Active" : "Previous",
                        actions: (<div>
                            <Menu.Menu position='right'  >
                                <Menu.Item >
                                    <Buuton2 style={{ backgroundColor: 'rgb(153,46,98)' }} primary>
                                        <Dropdown item text='Action'>
                                            <Dropdown.Menu style={{ marginTop: "10px", }}>
                                                <Dropdown.Item onClick={() => viewConfigDetail(row)}><VisibilityIcon /> View </Dropdown.Item>
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
                            width: '200%',
                            margingLeft: '250px',
                        },
                        filtering: false,
                        exportButton: false,
                        searchFieldAlignment: 'left',
                        pageSizeOptions: [10, 20, 100],
                        pageSize: 10,
                        debounceInterval: 400
                    }}
                />
            )}
            {showConfigView && (
                <>

                    <MaterialTable
                        icons={tableIcons}
                        title={"Config Information - " + configDetailObj.name}
                        columns={[
                            { title: " Module Name", field: "moduleName" },
                            {
                                title: "Version",
                                field: "version",
                            },
                        ]}
                        data={configDetailObj.configModules.map((row) => ({
                            //Id: manager.id,
                            moduleName: row.moduleName,
                            version: row.version,


                        }))}

                        options={{
                            headerStyle: {
                                backgroundColor: "#014d88",
                                color: "#fff",
                            },
                            searchFieldStyle: {
                                width: '200%',
                                margingLeft: '250px',
                            },
                            filtering: false,
                            exportButton: false,
                            searchFieldAlignment: 'left',
                            pageSizeOptions: [10, 20, 100],
                            pageSize: 10,
                            debounceInterval: 400
                        }}
                    />
                </>
            )}
            <UploadConfigFile serverConfigFile={ServerConfigFile} toggleModal={toggleModal} showModal={showModal} ServerUrl={serverConfig} />

        </div>
    );
}

export default ConfigFileList;



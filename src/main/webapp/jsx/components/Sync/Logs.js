import React, { useState, useEffect, forwardRef } from 'react';
import {
    Modal, ModalHeader, ModalBody, Form, FormFeedback,
    Row, Col, Card, CardBody
} from 'reactstrap';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { Alert } from 'reactstrap';
import { Spinner } from 'reactstrap';
import axios from "axios";
import { DropzoneArea } from 'material-ui-dropzone';
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';
import MaterialTable from "material-table";
import AddBox from "@material-ui/icons/AddBox";
import Check from "@material-ui/icons/Check";
import Clear from "@material-ui/icons/Clear";
import DeleteOutline from "@material-ui/icons/DeleteOutline";
import ChevronRight from "@material-ui/icons/ChevronRight";
import Edit from "@material-ui/icons/Edit";
import SaveAlt from "@material-ui/icons/SaveAlt";
import FilterList from "@material-ui/icons/FilterList";
import FirstPage from "@material-ui/icons/FirstPage";
import LastPage from "@material-ui/icons/LastPage";
import ChevronLeft from "@material-ui/icons/ChevronLeft";
import Search from "@material-ui/icons/Search";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import Remove from "@material-ui/icons/Remove";
import ViewColumn from "@material-ui/icons/ViewColumn";
import moment from "moment";
import { Button } from 'semantic-ui-react';
import { Typography } from '@material-ui/core';


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
    }
}))


const Logs = (props) => {
    const goBack = () =>{
        props.setShowErrorTable(false);
    }
    const classes = useStyles()

    const [messageLogsData, setMessageLogsData] = useState([]);
    const [tableName, setTableName] = useState();


    useEffect(()=> {
        if(props.rowObj){
            setMessageLogsData(props.rowObj.messageLog);
            setTableName( props.rowObj.tableName);
        }
    },[props.rowObj])



    //const samplesdispatched ={"sampleManifests": [] };
    const LogsProcess = e => {
        e.preventDefault()
        alert("Processing ")
        //Closing of the modal 
        props.togglestatus();



    }

    // function convertArrayToFormattedDate(dateArray) {
    //     const [year, month, day, hour, minute, second, millisecond] = dateArray;

    //     // Using Date to get timestamp
    //     const dateObject = new Date(year, month - 1, day, hour, minute, second, millisecond);
    //     const timestamp = dateObject.getTime();

    //     // Using moment to format the timestamp
    //     const formattedDate = moment(timestamp).format('MMM D, YYYY h:mm a');

    //     return formattedDate;
    // }

    const getRowCategoryColor = (category) => {
        if (category && category === "ERROR") {
            return "red";
        } else if (category && category === "WARNING") {
            return "orange";
        } else if (category && category === "SUCCESS") {
            return "green";
        } else {
            return "#000000";
        }
    }



    return (
        <div >

            <div style={{display:"flex", flexDirection:"row", justifyContent:"space-between", marginTop: "10px"}}>
            <Typography style={{fontWeight: 600, fontSize: "20px"}} toggle={props.toggleModal}>{`Message Logs  (${tableName})`}</Typography>
            <Button
                variant="contained"
                style={{ backgroundColor: "#014d88", }}
                className=" float-right mr-1"
                onClick={goBack}
            >
                <span style={{ textTransform: "capitalize", color: "#fff" }}>{"<< Back"}</span>
            </Button>
            </div>
            <br /><br />
            <MaterialTable
                icons={tableIcons}
                title={"JSON Files Logs "}
                columns={[
                    { title: "Module Check", field: "activity", filtering: false },
                    { title: "Name", field: "name", filtering: false },
                    { title: "Log Message", field: "logMessage", filtering: false },
                    { title: "Category", field: "category", filtering: false },
                    { title: "Others", field: "others", filtering: false },
                    { title: "Date-Time Created", field: "dateTimeCreated", filtering: false },
                ]}
                data={messageLogsData.map((row) => ({
                    //Id: manager.id,
                    activity: row.activity,
                    name: row.name,
                    logMessage: row.message,
                    category: (
                        <div>
                            <Typography style={{fontWeight: 600, color:getRowCategoryColor(row.category)}}>
                                {row.category}
                            </Typography>
                        </div>
                        ),
                    others: row.others,
                    // dateTimeCreated: convertArrayToFormattedDate(row.timeCreated),
                    dateTimeCreated: row.timeCreated,
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
                    exportButton: true,
                    searchFieldAlignment: 'left',
                    pageSizeOptions: [10, 20, 100],
                    pageSize: 10,
                    debounceInterval: 400
                }}
            />

        </div>
    );
}

export default Logs;

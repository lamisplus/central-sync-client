import React, {useState, useEffect, forwardRef} from 'react';
import {
    Modal, ModalHeader, ModalBody, Form, FormFeedback,
    Row, Col, Card, CardBody, FormGroup, Label, Input
} from 'reactstrap';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import { Alert } from 'reactstrap';
import { Spinner } from 'reactstrap';
import axios from "axios";

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
import Button from "@material-ui/core/Button";
import {token, url as baseUrl} from "../../../api";
import {toast} from "react-toastify";
import { Box, Typography } from '@material-ui/core';


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
        color: 'red'
    }
}))


const Index = (props) => {
    
    const classes = useStyles()
    const defaultValues = { appKey: "", facilityId:"", id:"", serverUrl:""}
    const [appKeyObj, setAppKeyObj] = useState(null);
    const [keyDetails, setKeyDetails] = useState(defaultValues);
    const [facilities, setFacilities] = useState( [])
    const [saving, setSaving] = useState(false);
    const [errors, setErrors] = useState({});
    const [key, setKey] = useState(null);
    const [fileContent, setFileContent] = useState('');
    
    useEffect(() => {
        Facilities();
        // AppKeyHistory();
        setKeyDetails(props.keyObj);
    }, [props]);
    // useEffect(() => {
    //     if (props.keyObj) {
    //         setKey(props.keyObj.appKey);
    //     }
    // }, [props.keyObj]);
    async function AppKeyHistory() {
        axios
            .get(`${baseUrl}sync/app-key`,
                { headers: {"Authorization" : `Bearer ${token}`} }
            )
            .then((response) => {
                setAppKeyObj(response.data);
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
    const validate = () => {
        let temp = { ...errors };
        temp.appKey = keyDetails.appKey
            ? ""
            : "Key is required";
        
        temp.serverUrl = keyDetails.serverUrl
            ? keyDetails.serverUrl.endsWith('/')
                ? "Server URL must not end with a slash"
                : ""
            : "Server URL is required";
        temp.facilityId = keyDetails.facilityId
            ? ""
            : "Facility is required";

        setErrors({
            ...temp,
        });
        return Object.values(temp).every((x) => x === "");
    };

    const handleInputChange = e => {
        setKeyDetails ({...keyDetails,  [e.target.name]: e.target.value});
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("its here");
        console.log(keyDetails);
        if (validate()) {
            console.log("now here");
            setSaving(true);
            axios.post(`${baseUrl}sync/app-key`,keyDetails,
                { headers: {"Authorization" : `Bearer ${token}`}},

            )
                .then(response => {
                    setSaving(false);
                    toast.success("APP KEY save successful")
                    props.showAppKeysListTable(true)
                    props.AppKeyHistory();
                })
                .catch(error => {
                    setSaving(false);
                    toast.error("Something went wrong");
                });
        };
    }
    //
    const showListiew =()=> {
        props.showAppKeysListTable(true)
        props.setShowViewKey(false)
    }


  const handleFileRead = (e) => {
    const file = e.target.files[0];

    if (file && file.name.endsWith('.key')) {
      const reader = new FileReader();

      reader.onload = (event) => {
        // Read the content of the file
        const content = event.target.result;
        console.log(content);
        setFileContent(content);
        setKeyDetails ({...keyDetails,  appKey: content});
      };


      reader.readAsText(file);
    } else {
      alert('Please select a valid .key file.');
    }
  };

    return (
        <div >

            <h1>App Key</h1>
            <br/>
            <Button
                variant="contained"
                color="primary"
                className=" float-right mr-1"
                //startIcon={<FaUserPlus />}
                style={{backgroundColor:"#014d88"}}
                onClick={showListiew}
            >
                <span style={{ textTransform: "capitalize" }}>{ "<< Back" } </span>
            </Button>
            <br/><br/>
            {!props.showViewKey ?
                (<>
                    <Row >
                        <Col md={12}>
                            <FormGroup>
                                <Label >Server URL *</Label>
                                <Input
                                    type="text"
                                    name="serverUrl"
                                    id="serverUrl"
                                    defaultValue={keyDetails?.serverUrl}
                                    onChange={handleInputChange}
                                    style={{border: "1px solid #014D88",borderRadius:"0.2rem"}}
                                    value={keyDetails?.serverUrl}
                                >

                                </Input>
                                {errors.serverUrl !=="" ? (
                                    <span className={classes.error}>{errors.serverUrl}</span>
                                ) : "" }
                            </FormGroup>
                        </Col>
                        <Col md={12}>
                            <FormGroup>
                                <Label >Facility *</Label>
                                <Input
                                    type="select"
                                    name="facilityId"
                                    id="facilityId"
                                    onChange={handleInputChange}
                                    style={{border: "1px solid #014D88",borderRadius:"0.2rem"}}
                                    value={keyDetails?.facilityId}
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
                                <Label >Key</Label>
                                <Input type="file" accept=".key" onChange={handleFileRead} />
                                {(fileContent || keyDetails?.appKey) && <Input
                                    type="textarea"
                                    name="appKey"
                                    id="appKey"
                                    defaultValue={keyDetails?.appKey}
                                    // value={keyDetails?.appKey}
                                    value={fileContent || keyDetails?.appKey}
                                    // onChange={handleInputChange}
                                    style={{border: "1px solid #014D88",borderRadius:"0.2rem",minHeight:"80px"}}
                                    required
                                    
                                />}
                                {errors.appKey !=="" ? (
                                    <span className={classes.error}>{errors.appKey}</span>
                                ) : "" }

                            </FormGroup>
                        </Col>

                    </Row>
                    {saving ? <Spinner /> : ""}
                    <br/>
                    <Button
                        type='submit'
                        variant='contained'
                        //color='primary'
                        style={{backgroundColor:'#014d88',fontWeight:"bolder"}}
                        //startIcon={<SettingsBackupRestoreIcon />}
                        onClick={handleSubmit}
                    >
                        <span style={{ textTransform: "capitalize ", color:"#fff" }}>Update Key</span>
                    </Button>
                </>)
                :
                (<>
                    {/* <Box maxWidth={"90%"} >
                        <Typography marginTop={2}>
                            APP KEY : {props.keyObj && props.keyObj!==null ? props.keyObj.appKey : ""}
                        </Typography>
                    </Box> */}
                
                </>)
            }

        </div>
    );
}

export default Index;

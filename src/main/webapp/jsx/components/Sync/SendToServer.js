import React, { useState, useEffect } from 'react';
import {
    Modal, ModalHeader, ModalBody, Form,
    Row, Col, Card, CardBody, FormGroup, Input, Label
} from 'reactstrap';
import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'

import { Spinner } from 'reactstrap';
import axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { token as token, url as baseUrl } from "./../../../api";
import { Box, CircularProgress, Typography } from '@material-ui/core';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import PendingOutlinedIcon from '@mui/icons-material/PendingOutlined';
import ProgressBar from "react-bootstrap/ProgressBar";
import { Varient } from '../Utils/UtilFunctions';


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
    header: {
        fontWeight: 600
    }
}))



const SendToServer = (props) => {
    const [rowObj, setRowObj] = useState(null);
    // const rowObj=props.rowObj!==null ? props.rowObj : {};
    const classes = useStyles()
    const [urlHide, setUrlHide] = useState(false);
    // const defaultValues = { username: "", password: "", syncHistoryUuid:rowObj.id, syncHistoryTrackerUuid:rowObj.uuid, facilityId: rowObj.organisationUnitId }
    const [patDetails, setPatDetails] = useState({});
    const [saving, setSaving] = useState(false);
    const [serverUrl, setServerUrl] = useState([])
    const [generatedFiles, setGeneratedFiles] = useState([])
    const [currentlyUploading, setCurrentlyUploading] = useState('')
    const [alreadyUploaded, setSetAlreadyUploaded] = useState([])
    const [errors, setErrors] = useState({});

    const toggleModal = () => {
        props.refreshPrevious();
        props.toggleModal();
    }

    // console.log("rowObj", props.rowObj);
    useEffect(() => {
        if (props.rowObj) {
            setRowObj(props.rowObj);
            setPatDetails({
                ...patDetails,
                syncHistoryUuid: props.rowObj.uuid,
                syncHistoryTrackerUuid: props.rowObj.syncHistoryTrackerUuid,
                facilityId: props.rowObj.organisationUnitId || props.rowObj.facilityId
            })
        }
    }, [props.rowObj])


    useEffect(() => {
        if (props.rowObj) {
            ServerUrl();
            GetGeneratedFiles();
        }
    }, [props.rowObj]);
    ///GET LIST OF Facilities
    async function ServerUrl() {
        axios
            .get(`${baseUrl}sync/remote-urls`,
                { headers: { "Authorization": `Bearer ${token}` } }
            )
            .then((response) => {
                setServerUrl(
                    Object.entries(response.data).map(([key, value]) => ({
                        label: value.url,
                        value: value.id,
                    }))
                );
            })
            .catch((error) => {

            });

    }

    const handleInputChange = e => {
        setPatDetails({ ...patDetails, [e.target.name]: e.target.value });
    }
    /*****  Validation */
    const validate = () => {
        let temp = { ...errors };
        temp.username = patDetails.username
            ? ""
            : "Username is required";
        temp.password = patDetails.password
            ? ""
            : "Password is required";

        setErrors({
            ...temp,
        });
        return Object.values(temp).every((x) => x === "");
    };

    async function GetGeneratedFiles() {
        if (!props.rowObj.syncHistoryTrackerUuid) {
            axios
                .get(`${baseUrl}sync/history/${props?.rowObj?.id}/tracker`,
                    { headers: { "Authorization": `Bearer ${token}` } }
                )
                .then((response) => {
                    const data = response.data;
                    setGeneratedFiles(data);
                    setSetAlreadyUploaded(data.filter((item) => item.status.toLowerCase() === 'synced')
                        .map((each) => each.fileName));
                })
                .catch((error) => {
                });
        } else { 
            setGeneratedFiles([props.rowObj]);
            setSetAlreadyUploaded([]);
        }
    }

    const returnUploadStatus = (fileName) => {
        if (currentlyUploading === fileName) {
            return (
                <Box display={'flex'} justifyContent={'flex-start'}>
                    <CircularProgress style={{ marginRight: "10px", width: "20px", height: "20px", color: "blue" }} />
                    <Typography style={{ fontWeight: "600", color: "blue" }}>Uploading...</Typography>
                </Box>
            );
        }

        if (alreadyUploaded.includes(fileName)) {
            return (
                <Box display={'flex'} justifyContent={'flex-start'}>
                    <CheckCircleOutlineIcon style={{ marginRight: "10px", color: "green" }} />
                    <Typography style={{ fontWeight: "600", color: "green" }}>Uploaded</Typography>
                </Box>
            );
        }
        else {
            return (
                <Box display={'flex'} justifyContent={'flex-start'}>
                    <PendingOutlinedIcon style={{ marginRight: "10px", color: "gray" }} />
                    <Typography style={{ fontWeight: "600", color: "grey" }}>Pending</Typography>
                </Box>
            );
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (validate()) {
            var fileUploadErrors = [];
            var errorCount = 0;
            try {
                setSaving(true);
                for (let index = 0; index < generatedFiles.length; index++) {
                    const element = generatedFiles[index];
                    // console.log(element.fileName, element);
                    if (alreadyUploaded.includes(element.fileName)) {
                        continue;
                    }
                    const dataToBeSent = {
                        username: patDetails.username,
                        password: patDetails.password,
                        syncHistoryUuid: element.syncHistoryUuid,
                        syncHistoryTrackerUuid: element.uuid,
                        facilityId: element.organisationUnitId || element.facilityId
                    }
                    setCurrentlyUploading(element.fileName);
                    await axios.post(`${baseUrl}export/file/data`, dataToBeSent,
                        { headers: { "Authorization": `Bearer ${token}` } },

                    )
                        .then(response => {
                            setCurrentlyUploading('');
                            setSetAlreadyUploaded((prev) => [...prev, element.fileName])
                        })
                        .catch(error => {
                            setCurrentlyUploading('');
                            fileUploadErrors.push(element.fileName);
                            errorCount++;
                        });
                }

            } catch (error) {
                console.log(error);

            } finally {
                setSaving(false);
                if (errorCount > 0) {
                    toast.error(`File upload incomplete. Error uploading ${fileUploadErrors.join(', ')}. Please check your internet connection, and try again.`);
                    props.refreshPrevious();
                } else {
                    toast.success("Sync Successful. All files uploaded successfully.");
                    props.refreshPrevious();
                }
            }};
    }

    const getUploadPercentage = () => {
        return (alreadyUploaded.length / generatedFiles.length) * 100
    }


    return (
        <div >

            <Modal isOpen={props.showModal} toggle={toggleModal} className={props.className} size="lg" backdrop="static">
                <Form >
                    <ModalHeader className={classes.header} toggle={props.toggleModal}>SEND TO SERVER </ModalHeader>
                    <ModalBody>
                        <Typography fontSize={"14px"} marginBottom={"10px"}>
                            {`Sending `} <span style={{ fontWeight: "600" }}>{`${props?.rowObj?.tableName || props?.rowObj?.fileName}`}</span>
                        </Typography>
                        <Card >
                            <CardBody>
                                <Row >

                                    <Col md={12}>
                                        <FormGroup>
                                            <Label >Username </Label>
                                            <Input
                                                type="text"
                                                name="username"
                                                id="username"
                                                value={patDetails.username}
                                                onChange={handleInputChange}
                                                style={{ border: "1px solid #014D88", borderRadius: "0.2rem" }}
                                                required
                                            />
                                            {errors.username !== "" ? (
                                                <span className={classes.error}>{errors.username}</span>
                                            ) : ""}
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label >Password </Label>
                                            <Input
                                                type="password"
                                                name="password"
                                                id="password"
                                                value={patDetails.password}
                                                onChange={handleInputChange}
                                                style={{ border: "1px solid #014D88", borderRadius: "0.2rem" }}
                                                required
                                            />
                                            {errors.password !== "" ? (
                                                <span className={classes.error}>{errors.password}</span>
                                            ) : ""}
                                        </FormGroup>
                                    </Col>
                                </Row>
                                {saving ? <Spinner /> : ""}
                                <br />
                                <Button
                                    type='submit'
                                    variant='contained'
                                    disabled={(alreadyUploaded.length === generatedFiles.length) || saving}
                                    style={{ backgroundColor: '#014d88', fontWeight: "bolder" }}
                                    //startIcon={<SettingsBackupRestoreIcon />}
                                    onClick={handleSubmit}
                                >
                                    <span style={{ textTransform: "capitalize ", color: "#fff" }}>Send To Server</span>
                                </Button>
                            </CardBody>
                        </Card>
                        <div style={{ width: "100%", height: "100%", display:"flex", flexDirection: "column", alignItems:"center", justifyContent: "flex-start"}}>
                        {saving && <Col md={12}>
                    <Typography style={{ fontSize: "14px", color: "orange", fontWeight: "600" }}>
                        Files are being uploaded. Please do not refresh this page.
                    </Typography>
                </Col>}
                        <ProgressBar
                            now={getUploadPercentage()}
                            variant={Varient(getUploadPercentage())}
                            style={{width:"100%", marginTop:"10px", marginBottom:"10px"}}
                            // label={`${row.percentageSynced}%`}
                        />
                        <Typography>{`${getUploadPercentage().toFixed(0)}%`}</Typography>
                    </div>
                        <Box maxHeight={"400px"} marginTop={5} overflow={'auto'}>
                            {generatedFiles.map((file, index) => (
                                <Box key={index} display={'flex'} height={"40px"} justifyContent={'flex-start'} paddingX={2}>
                                    <Typography style={{ width: "70%" }}>
                                        {file.fileName}
                                    </Typography>
                                    {returnUploadStatus(file.fileName)}
                                </Box>
                            ))}
                        </Box>
                    </ModalBody>
                </Form>
            </Modal>
        </div>
    );
}

export default SendToServer;

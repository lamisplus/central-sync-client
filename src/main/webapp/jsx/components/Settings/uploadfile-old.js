import React, {useState, useEffect} from 'react';
import { Modal, ModalHeader, ModalBody,Form,FormFeedback,
    Row,Col, Card,CardBody, FormGroup, Input, Label, Table} from 'reactstrap';
import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'

import { Spinner } from 'reactstrap';
import axios from "axios";
import {  toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { token as token,  url as baseUrl } from "./../../../api";


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



const NewToken = (props) => {
    const classes = useStyles()
    const [urlHide, setUrlHide] = useState(false);
    const defaultValues = { file: "",}
    const [patDetails, setPatDetails] = useState(defaultValues);
    const [saving, setSaving] = useState(false);
    const [serverUrl, setServerUrl] = useState( [])
    const [errors, setErrors] = useState({});
    const [dataJSONFile, setDataJSONFile] = useState(null)
    const [dataJSONFileContent, setDataJSONFileContent] = useState(null)

    useEffect(() => {
        ServerUrl()
    }, []);
    ///GET LIST OF Facilities
    async function ServerUrl() {
        axios
            .get(`${baseUrl}sync/remote-urls`,
                { headers: {"Authorization" : `Bearer ${token}`} }
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
        setPatDetails ({...patDetails,  [e.target.name]: e.target.value});
    }
    /*****  Validation */
    const validate = () => {
        let temp = { ...errors };
        temp.file = patDetails.file
            ? ""
            : "Username is required";

        setErrors({
            ...temp,
        });
        return Object.values(temp).every((x) => x === "");
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (validate()) {
            setSaving(true);
            axios.post(`${baseUrl}sync/remote-access-token`,patDetails,
                { headers: {"Authorization" : `Bearer ${token}`}},

            )
                .then(response => {
                    setSaving(false);
                    props.ServerUrl()
                    toast.success("Token Generated Successful");
                    props.toggleModal()

                })
                .catch(error => {
                    setSaving(false);
                    toast.error("Something went wrong");
                    props.toggleModal();
                });
        };
    }

    const readFileOnUpload = (uploadedFile) =>{
        const fileReader = new FileReader();
        fileReader.onloadend = ()=>{
            try{
                setDataJSONFile(JSON.parse(fileReader.result));
                //console.log(JSON.parse(fileReader.result))
                setDataJSONFileContent(fileReader.result)
            }catch(e){
                setDataJSONFile("**Not valid JSON file!**");
            }
        }
        if( uploadedFile!== undefined)
            fileReader.readAsText(uploadedFile);
    }

    return (
        <div >

            <Modal isOpen={props.showModal} toggle={props.toggleModal} className={props.className} size="lg" backdrop={false} backdrop="static">
                <Form >
                    <ModalHeader toggle={props.toggleModal}>Personal Access Token </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label >Config File</Label>
                                            <Input
                                                type="file"
                                                name="file"
                                                id="file"
                                                value={patDetails.file}
                                                onChange={(e)=>readFileOnUpload(e.target.files[0])}
                                                style={{border: "1px solid #014D88",borderRadius:"0.2rem"}}
                                                required
                                            />
                                            {errors.file !=="" ? (
                                                <span className={classes.error}>{errors.url}</span>
                                            ) : "" }

                                        </FormGroup>
                                    </Col>
                                    {dataJSONFile!==null &&(
                                        <>
                                            <Table striped>
                                                <thead>
                                                <tr>
                                                    <th>
                                                        #
                                                    </th>
                                                    <th>
                                                        Module Name
                                                    </th>
                                                    <th>
                                                        Version
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>

                                                {
                                                    dataJSONFile.configModules.map((numList,i) =>(
                                                        <tr key={i}>
                                                            <td key={i}>{i}</td>
                                                            <td key={i}>{numList.moduleName}</td>
                                                            <td key={i}>{numList.version}</td>
                                                        </tr>
                                                    ))
                                                }
                                                </tbody>
                                            </Table>
                                        </>

                                    )}
                                    <Input
                                        type="textarea"
                                        name="content"
                                        cols={5}
                                        row={5}
                                        id="file"
                                        value={dataJSONFileContent}

                                    />
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
                                    <span style={{ textTransform: "capitalize ", color:"#fff" }}>Upload COnfig File</span>
                                </Button>
                            </CardBody>
                        </Card>
                    </ModalBody>
                </Form>
            </Modal>
        </div>
    );
}

export default NewToken;

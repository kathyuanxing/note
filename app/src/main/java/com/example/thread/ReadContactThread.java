package com.example.thread;

import android.content.Context;
import android.util.Log;

import com.example.dao.ContactDao;
import com.example.entity.Contact;
import com.example.testandroid.MenuActivity;
import com.example.util.Constants;
import com.example.dao.ContactDao;
import com.example.util.FileUtils;
import com.example.util.MSharedPreference;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by kathy on 2015/12/27.
 */

public class ReadContactThread extends Thread {
    private final String TAG=ReadContactThread.class.getCanonicalName();
    Constants constants = new Constants();
    private ContactDao contactDao;
    private FileUtils fileUtils;
    private String dirPath;
    private Context context;
    private String name = "name";
    private String siptel1 = "siptel1";
    private String siptel2 = "siptel2";
    private String nickname = "nickname";
    private String photopath = "photopath";
    private String province = "province";
    private String title = "";
    private String post = "post";
    private String mobileStr = "mobile";
    private String chushi = "chushi";
    private String workvoice = "workvoice";
    private String ord = "ord";
    private String fax = "fax";
    private String message = "message";
    private long mobile = 0;

    public ReadContactThread(Context context) {
        this.context = context;
        contactDao = new ContactDao(context);
        contactDao.open();
        // 新建存放图片的文件夹
        fileUtils = new FileUtils();
        File dirFile = fileUtils.createSDDir("contactimage");
        dirPath = dirFile.getPath() + "/";
    }

    private String generateLocalImagePath(String imageName) {
        String localImagetPath = dirPath + imageName + ".png";
        return localImagetPath;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            URL contactUrl = new URL(constants.CONTACT_URL);
            HttpURLConnection connection = (HttpURLConnection) contactUrl
                    .openConnection();

            connection.setConnectTimeout(10000);
            in = connection.getInputStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(in);
            Long lastModified=connection.getLastModified();


            Log.d(TAG, "lastModified="+lastModified.toString()+" 通讯录有更新，开始更新通讯录");



            // 根标签 peoples
            Element rootPeoples = doc.getDocumentElement();
            if (rootPeoples == null) {
                MenuActivity.contactUpdating=false;
                return;
            }
            Log.d(TAG, rootPeoples.toString()) ;
            // 子标签 department
            NodeList departmentList = rootPeoples.getChildNodes();
            if (departmentList == null) {
                MenuActivity.contactUpdating=false;
                return;
            }
            // 建立主键
            int contact_id = 0;

            for (int i = 0; i < departmentList.getLength(); i++) {
                Node department = departmentList.item(i);
                String s=department.getNodeValue();
                //if(s==null||s.startsWith("\n"))
                //	continue;
                String departmentId = department.getAttributes()
                        .getNamedItem("id").getNodeValue();
                String departmentName = department.getAttributes()
                        .getNamedItem("name").getNodeValue();
                NodeList peopleList = department.getChildNodes();
                if (peopleList == null) {
                    continue;
                }
                for (int j = 0; j < peopleList.getLength(); j++) {
                    Node people = peopleList.item(j);
                    if(people.getNodeValue()=="\n")
                        continue;
                    Contact contact = new Contact();
                    String peopleId = people.getAttributes().getNamedItem("id")
                            .getNodeValue();
                    contact.setContact_id(contact_id);
                    contact_id++;
                    contact.setContact_userid(peopleId);
                    contact.setContact_department(departmentName);
                    contact.setContact_department_id(departmentId);

                    NodeList peopleChildNode = people.getChildNodes();
                    if (peopleChildNode.item(0).getFirstChild() != null) {
                        name = peopleChildNode.item(0).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_name(name);

                    if (peopleChildNode.item(1).getFirstChild() != null) {
                        siptel1 = peopleChildNode.item(1).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_siptel1(siptel1);

                    if (peopleChildNode.item(2).getFirstChild() != null) {
                        siptel2 = peopleChildNode.item(2).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_siptel2(siptel2);

                    if (peopleChildNode.item(3).getFirstChild() != null) {
                        nickname = peopleChildNode.item(3).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_nickname(nickname);

                    if (peopleChildNode.item(4).getFirstChild() != null) {
                        photopath = peopleChildNode.item(4).getFirstChild()
                                .getNodeValue();
                    }

                    String localImagePath = generateLocalImagePath(peopleId);
                    contact.setContact_image_path(localImagePath);
                    if ((!photopath.equals("null"))
                            && (!photopath.equals("photopath"))) {
                        new ReadImageThread(photopath, localImagePath).start();
                    }

                    if (peopleChildNode.item(6).getFirstChild() != null) {
                        province = peopleChildNode.item(6).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_province(province);

                    if (peopleChildNode.item(7).getFirstChild() != null) {
                        title = peopleChildNode.item(7).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_position(title);


                    if (peopleChildNode.item(8).getFirstChild() != null) {
                        post = peopleChildNode.item(8).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_post(post);

                    if (peopleChildNode.item(9).getFirstChild() != null) {
                        mobileStr = peopleChildNode.item(9).getFirstChild()
                                .getNodeValue();
                        mobile = Long.parseLong(mobileStr);
                    }
                    contact.setContact_telephone(mobile);

                    if (peopleChildNode.item(10).getFirstChild() != null) {
                        chushi = peopleChildNode.item(10).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_chushi(chushi);

                    if (peopleChildNode.item(11).getFirstChild() != null) {
                        workvoice = peopleChildNode.item(11).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_workvoice(workvoice);

                    if (peopleChildNode.item(12).getFirstChild() != null) {
                        ord = peopleChildNode.item(12).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_ord(ord);

                    if (peopleChildNode.item(13).getFirstChild() != null) {
                        fax = peopleChildNode.item(13).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_fax(fax);

                    if (peopleChildNode.item(14).getFirstChild() != null) {
                        message = peopleChildNode.item(14).getFirstChild()
                                .getNodeValue();
                    }
                    contact.setContact_message(message);

                    // update/insert
                    if (!contactDao.updateContact(contact)) {
                        contactDao.insertIntoTableContact(contact);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MenuActivity.contactUpdating=false;
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 获取当前登录用户的相关信息，并保存
            Contact contact = contactDao.getContactBySiptel(MSharedPreference
                    .get(context, MSharedPreference.Siptel, ""));
            if (contact != null) {
                MSharedPreference.save(context, MSharedPreference.USER_ID,
                        contact.getContact_userid());
                MSharedPreference.save(context, MSharedPreference.USER_NAME,
                        contact.getContact_name());
                Constants.USER_NAME = contact.getContact_name();
            }

            contactDao.close();
        }
    }
}

package com.aqsystem.aqsystem.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/23 0023.
 */

public class Article {

    /**
     * rcCode : success
     * msg : 成功
     * data : [{"id":"1","isNewRecord":false,"createDate":"2013-05-27 08:00:00","updateDate":"2017-04-15 18:06:02","category":{"id":"3","isNewRecord":false,"name":"网站简介","sort":30,"module":"","inMenu":"0","inList":"1","showModes":"0","allowComment":"0","isAudit":"0","url":"/anqun/f/list-3.html","ids":"3","root":false,"parentId":"0"},"title":"测试title","link":"","color":"green","image":"","keywords":"title","description":"私搭乱建发上来的开发商了对方","weight":999,"weightDate":1494000000000,"hits":1,"posid":",null,","customContentView":"","viewConfig":"","user":{"isNewRecord":true,"name":"系统管理员","loginFlag":"1","roleNames":"","admin":false},"url":"/anqun/f/view-3-1.html","posidList":["null"],"imageSrc":""}]
     */

    private String rcCode;
    private String msg;
    private List<DataBean> data;

    public String getRcCode() {
        return rcCode;
    }

    public void setRcCode(String rcCode) {
        this.rcCode = rcCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * isNewRecord : false
         * createDate : 2013-05-27 08:00:00
         * updateDate : 2017-04-15 18:06:02
         * category : {"id":"3","isNewRecord":false,"name":"网站简介","sort":30,"module":"","inMenu":"0","inList":"1","showModes":"0","allowComment":"0","isAudit":"0","url":"/anqun/f/list-3.html","ids":"3","root":false,"parentId":"0"}
         * title : 测试title
         * link :
         * color : green
         * image :
         * keywords : title
         * description : 私搭乱建发上来的开发商了对方
         * weight : 999
         * weightDate : 1494000000000
         * hits : 1
         * posid : ,null,
         * customContentView :
         * viewConfig :
         * user : {"isNewRecord":true,"name":"系统管理员","loginFlag":"1","roleNames":"","admin":false}
         * url : /anqun/f/view-3-1.html
         * posidList : ["null"]
         * imageSrc :
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private CategoryBean category;
        private String title;
        private String link;
        private String color;
        private String image;
        private String keywords;
        private String description;
        private int weight;
        private long weightDate;
        private int hits;
        private String posid;
        private String customContentView;
        private String viewConfig;
        private UserBean user;
        private String url;
        private String imageSrc;
        private List<String> posidList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public long getWeightDate() {
            return weightDate;
        }

        public void setWeightDate(long weightDate) {
            this.weightDate = weightDate;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public String getPosid() {
            return posid;
        }

        public void setPosid(String posid) {
            this.posid = posid;
        }

        public String getCustomContentView() {
            return customContentView;
        }

        public void setCustomContentView(String customContentView) {
            this.customContentView = customContentView;
        }

        public String getViewConfig() {
            return viewConfig;
        }

        public void setViewConfig(String viewConfig) {
            this.viewConfig = viewConfig;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        public List<String> getPosidList() {
            return posidList;
        }

        public void setPosidList(List<String> posidList) {
            this.posidList = posidList;
        }

        public static class CategoryBean {
            /**
             * id : 3
             * isNewRecord : false
             * name : 网站简介
             * sort : 30
             * module :
             * inMenu : 0
             * inList : 1
             * showModes : 0
             * allowComment : 0
             * isAudit : 0
             * url : /anqun/f/list-3.html
             * ids : 3
             * root : false
             * parentId : 0
             */

            private String id;
            private boolean isNewRecord;
            private String name;
            private int sort;
            private String module;
            private String inMenu;
            private String inList;
            private String showModes;
            private String allowComment;
            private String isAudit;
            private String url;
            private String ids;
            private boolean root;
            private String parentId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public String getInMenu() {
                return inMenu;
            }

            public void setInMenu(String inMenu) {
                this.inMenu = inMenu;
            }

            public String getInList() {
                return inList;
            }

            public void setInList(String inList) {
                this.inList = inList;
            }

            public String getShowModes() {
                return showModes;
            }

            public void setShowModes(String showModes) {
                this.showModes = showModes;
            }

            public String getAllowComment() {
                return allowComment;
            }

            public void setAllowComment(String allowComment) {
                this.allowComment = allowComment;
            }

            public String getIsAudit() {
                return isAudit;
            }

            public void setIsAudit(String isAudit) {
                this.isAudit = isAudit;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getIds() {
                return ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public boolean isRoot() {
                return root;
            }

            public void setRoot(boolean root) {
                this.root = root;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }
        }

        public static class UserBean {
            /**
             * isNewRecord : true
             * name : 系统管理员
             * loginFlag : 1
             * roleNames :
             * admin : false
             */

            private boolean isNewRecord;
            private String name;
            private String loginFlag;
            private String roleNames;
            private boolean admin;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLoginFlag() {
                return loginFlag;
            }

            public void setLoginFlag(String loginFlag) {
                this.loginFlag = loginFlag;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }
        }
    }
}

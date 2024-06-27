package cn.iocoder.yudao.module.system.config;

public class WxworkAppletProperties {
        public WxworkAppletProperties(String corpId, String agentId, String secret) {
            this.corpId = corpId;
            this.agentId = agentId;
            this.secret = secret;
        }

        public WxworkAppletProperties() {
        }

        /**
         * 企业id
         */
        private String corpId;

        /**
         * 应用 ID
         */
        private String agentId;

        /**
         * 应用密钥
         */
        private String secret;

        public String getCorpId() {
            return corpId;
        }

        public void setCorpId(String corpId) {
            this.corpId = corpId;
        }

        public String getAgentId() {
            return agentId;
        }

        public void setAgentId(String agentId) {
            this.agentId = agentId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
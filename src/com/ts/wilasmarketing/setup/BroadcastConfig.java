package com.ts.wilasmarketing.setup;

import com.ts.wilasmarketing.base.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BroadcastConfig {


  private TreeMap<String, CampaignConfig> lookUps;
  private List<CampaignConfig> campaigns;

  private static BroadcastConfig theInstance;

  private boolean test = false;

  private boolean noCampaignConfiguration = true;

  static public BroadcastConfig getInstance() {
    if (theInstance == null) {
      synchronized (BroadcastConfig.class) {
        if (theInstance == null) {
          theInstance = new BroadcastConfig();
        }
      }
    }
    return theInstance;
  }

  private BroadcastConfig() {
    lookUps = new TreeMap<String, CampaignConfig>();
    campaigns = new ArrayList<CampaignConfig>();

    if (test) populateForTest();
    else init();
  }

  public List<CampaignConfig> getCampaigns() {
    return campaigns;
  }

  public CampaignConfig getCampaignConfig(String campaignId) {
    CampaignConfig config = lookUps.get(campaignId);
    if (config != null) return config;
    if (noCampaignConfiguration) {

      CampaignConfig c1 = new CampaignConfig();
      c1.setId(campaignId);

      SMSResource res1 = new SMSResource();
      res1.setConnectorId("DLGMT");
      res1.setShortCode("TEST");

      c1.setSmsResource(res1);
      campaigns.add(c1);
      lookUps.put(c1.getId(), c1);

      return c1;
    }
    return null;
  }

  public void setCampaigns(List<CampaignConfig> campaigns) {
    this.campaigns = campaigns;
  }

  void init() {

    if (!noCampaignConfiguration) {
      Mapper mapper = new Mapper();
      CampaignConfig[] objs = mapper.getCampaignsConfig(Utils.getFileContent("config/campaigns.cfg"));

      for (CampaignConfig cfg : objs) {
        lookUps.put(cfg.getId(), cfg);
        campaigns.add(cfg);
      }
    }

  }

  void populateForTest() {

    CampaignConfig c1 = new CampaignConfig();
    c1.setId("camp1");
    SMSResource res1 = new SMSResource();
    res1.setConnectorId("conn1");
    c1.setSmsResource(res1);
    campaigns.add(c1);

    CampaignConfig c2 = new CampaignConfig();
    c2.setId("camp2");
    SMSResource res2 = new SMSResource();
    res2.setConnectorId("conn2");
    c2.setSmsResource(res2);
    campaigns.add(c2);

  }

  public static void main(String[] args) {

    Mapper mapper = new Mapper();

    BroadcastConfig config = new BroadcastConfig();
//    config.populateForTest();

    System.out.println(mapper.getPrettyFormat(config));

  }


}

package com.meteor.remote.core.models;

import java.util.HashMap;
import java.util.Map;

///
/// \brief The OWN_ZoneToWhereTable class is a utility
/// that maps a zone string to an Open-Web-Net 'where' information
///
public class OWN_ZoneToWhereTable {

   public OWN_ZoneToWhereTable() {
   }

   ///
   /// \brief insert an entry in zone to where table
   /// \param zone
   /// \param where
   ///
   public void insert(String zone, String where) {
      _zoneWhereTable.put( zone, where);
   }

   ///
   /// \brief retrieve a 'where' for a given \p zone
   /// \param zone
   /// \return the zone or an empty string if no match is found
   ///
   public String where(String zone) {
      return _zoneWhereTable.get( zone);
   }

   private Map<String, String> _zoneWhereTable = new HashMap<>();
}


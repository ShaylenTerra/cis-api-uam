package com.dw.ngms.cis.service.mapper.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.DocketList;
import com.dw.ngms.cis.service.dto.examination.dockets.DocketListDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocketListDecorator implements DocketListMapper{

    @Override
    public DocketList DocketListDtoToDocketList(DocketListDto docketListDto) {
        DocketList docketList = new DocketList();

        docketList.setItemCode(docketListDto.getItemCode());
        docketList.setName(docketListDto.getName());
        docketList.setDescription(docketListDto.getDescription());
        docketList.setParentId(docketListDto.getParentId());
        docketList.setDocketTypeId(docketListDto.getDocketTypeId());
        docketList.setStatus(docketListDto.getStatus());
        docketList.setValue(docketListDto.getValue());

        return docketList;
    }

    @Override
    public DocketListDto DocketListToDocketListDto(DocketList docketList) {
        DocketListDto docketListDto = new DocketListDto();

        docketListDto.setItemCode(docketList.getItemCode());
        docketListDto.setName(docketList.getName());
        docketListDto.setDescription(docketList.getDescription());
        docketListDto.setParentId(docketList.getParentId());
        docketListDto.setDocketTypeId(docketList.getDocketTypeId());
        docketListDto.setStatus(docketList.getStatus());
        docketListDto.setValue(docketList.getValue());

        return docketListDto;
    }

    public List<DocketListDto> ListOfDocketListToListOfDocketListDto(List<DocketList> docketList) {
        List<DocketListDto> listOfdocketListDtos = new ArrayList<>();

        for(int i = 0; i < docketList.size();i++){
            DocketListDto docketListDto = new DocketListDto();

            docketListDto.setItemCode(docketList.get(i).getItemCode());
            docketListDto.setName(docketList.get(i).getName());
            docketListDto.setDescription(docketList.get(i).getDescription());
            docketListDto.setParentId(docketList.get(i).getParentId());
            docketListDto.setDocketTypeId(docketList.get(i).getDocketTypeId());
            docketListDto.setStatus(docketList.get(i).getStatus());
            docketListDto.setValue(docketList.get(i).getValue());

            listOfdocketListDtos.add(docketListDto);
        }
        return listOfdocketListDtos;
    }

    //utility method

   public HashMap<Long,String[]> mapParentWithChild(String temp){
       HashMap<Long,String>global = new HashMap<>();
       HashMap<Long, String[]> map = new HashMap<>();
       HashMap<Long,String[]> map2 = new HashMap<>();

       List<String[]>list1 = new ArrayList<>();
       List<String[]>list2 = new ArrayList<>();

       String[] t0 = temp.split("=");

       String[] t1 = t0[1].split(";");
       String[] t1_ = t0[0].split(";");


       for(int i = 0 ; i < t1.length;i++){
           String t2 = t1[i].substring(1, t1[i].length()-1);
           String t2_ = t1_[i].substring(1, t1_[i].length()-1);

           String []t3 = t2.split(":");
           String []t3_ = t2_.split(":");

           map.put(Long.valueOf(t3[0]),t3[1].split(","));
           map2.put(Long.valueOf(t3_[0]),t3_[1].split(","));

           list1.add(t3[1].split(","));
           list2.add(t3_[1].split(","));
       }

       for(int n = 0; n<list1.size() ;n++ ){
           for(int o = 0 ; o < list1.get(n).length ; o++){
               String[] arr = list1.get(o);
               String[] arr2 = list2.get(o);
               global.put(Long.valueOf(arr2[o]),arr[o]);
           }
       }

       /* for(String[]t4: map.values()){
            for(int k = 0 ; k< t4.length ; k++){
                System.out.println(t4[k]);
            }
        }

        for(String[]t4_: map2.values()){
            for(int k = 0 ; k< t4_.length ; k++){
                System.out.println(t4_[k]);
            }
        }*/

       //System.out.println(map.keySet().toString());
       // System.out.println(map.entrySet().toString());
       return null;
   }




}

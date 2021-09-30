package com.example.hello.service;


import com.example.hello.dao.StudentRepository;
import com.example.hello.dto.AddressDto;
import com.example.hello.dto.StudentDto;
import com.example.hello.entity.AddressEntity;
import com.example.hello.entity.StudentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent(StudentDto studentDto){
        StudentDetails studentDetails=new StudentDetails();
        AddressEntity addressEntity=new AddressEntity();
        studentDetails.setName(studentDto.getName());
        studentDetails.setFname(studentDto.getFname());
        addressEntity.setCountry(studentDto.getAddressDto().getCountry());
        addressEntity.setPinCode(studentDto.getAddressDto().getPinCode());
        addressEntity.setStreetLine1(studentDto.getAddressDto().getStreetLine1());
        studentDetails.setAddress(addressEntity);


        studentRepository.save(studentDetails);



    }
    public List<StudentDto> getall(){
        List<StudentDetails> studentDetails=studentRepository.findAll();
        List<StudentDto> studentDtos = new ArrayList<>();
        for(int i=0;i<studentDetails.size();i++){
            StudentDto studentDto=new StudentDto();
            AddressDto addressDto = new AddressDto();
            if(studentDetails.get(i).getAddress()!=null) {
                addressDto.setCountry(studentDetails.get(i).getAddress().getCountry());
                addressDto.setPinCode(studentDetails.get(i).getAddress().getPinCode());
                addressDto.setStreetLine1(studentDetails.get(i).getAddress().getStreetLine1());
            }
            studentDto.setName(studentDetails.get(i).getName());
            studentDto.setFname(studentDetails.get(i).getFname());
            studentDto.setId(studentDetails.get(i).getId());
            studentDto.setAddressDto(addressDto);
            studentDtos.add(studentDto);

        }

            

        return studentDtos;

    }

    public StudentDto getById(Long id) {
       Optional<StudentDetails>op= studentRepository.findById(id);

       if(op.isPresent()){
           StudentDto studentDto=new StudentDto();
           studentDto.setName(op.get().getName());
           studentDto.setFname(op.get().getFname());
           studentDto.setId(op.get().getId());
           return studentDto;
       }
       return null;
    }

    public List<StudentDto> getByName(String name) {
        List<StudentDetails> studentDetails = studentRepository.findByName(name);
        List<StudentDto> studentDtos = new ArrayList<>();
        for (int i = 0; i < studentDetails.size(); i++) {
            StudentDto studentDto = new StudentDto();
            studentDto.setName(studentDetails.get(i).getName());
            studentDto.setFname(studentDetails.get(i).getFname());
            studentDto.setId(studentDetails.get(i).getId());
            studentDtos.add(studentDto);


        }
        return studentDtos;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);

    }

    public void updateStudent(Long id, StudentDto studentDto) {
        Optional<StudentDetails> studentDetails = studentRepository.findById(id);
        if (studentDetails.isPresent()) {
            studentDetails.get().setName(studentDto.getName());
            studentDetails.get().setFname(studentDto.getFname());
            studentRepository.saveAndFlush(studentDetails.get());

        } else {
            //we need to throw exception here;
        }
    }


////    private ArrayList<StudentDto> studentDtos = new ArrayList<>();
//    public void saveStudent(StudentDto studentDto){
//        studentDtos.add(studentDto);
//    }
//    public ArrayList<StudentDto> getStudentDtos() {
//        return studentDtos;
//    }

//    public StudentDto getByid(int id){
//Optional<StudentDto> op= studentDtos.stream().filter(x->x.getId()==id).findAny();
//if(op.isPresent()){
//    return op.get();
//}
//return null;
//    }
//    public List<StudentDto> getByname(String name){
//
//     // Optional<StudentDto> op= Optional.ofNullable(studentDtos.stream().filter(x -> x.getName(x.getName())).findAny().orElse(null));
//       return studentDtos.stream().filter(x->x.getName().equals(name)).collect(Collectors.toList());
////      if(op.isPresent()){
////          return op.get();
////      }
//      //        for(StudentDto studentDto:studentDtos){
////            if(studentDto.getName().equals(name)){
////                return studentDto;
////            }
////        }
//
//    }
//    //TO find any by initials
//    public List<StudentDto> getByini(String name){
//       return studentDtos.stream().filter(x->x.getName().matches("kas")).collect(Collectors.toList());
//
//    }
//    public List<String> getBycountry(String country){
//         // Map<AddressDto,StudentDto> cou=studentDtos.stream().collect(Collectors.toMap(StudentDto::getAddressDto, Function.identity()));
//         List<String> student=studentDtos.stream().filter(x->x.getAddressDto().getCountry().equals(country)).map(StudentDto::getName).collect(Collectors.toList());
//          return student;
//            }
//
//
}

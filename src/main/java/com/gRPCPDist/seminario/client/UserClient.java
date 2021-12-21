package com.gRPCPDist.seminario.client;

import com.proto.user.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserClient {

    public static void main(String [] args){
        UserClient user = new UserClient();
        user.execute();
    }

    private void execute(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();

        UserServiceGrpc.UserServiceBlockingStub userClient = UserServiceGrpc.newBlockingStub(channel);

        //Criando usuario

        User user = User.newBuilder()
                .setName("Carlos")
                .setEmail("carlos.nunes@academico.ifpb.edu.br")
                .build();
        CreateUserResponse  createUserResponse = userClient.createUser(
                CreateUserRequest.newBuilder().setUser(user).build()
        );

        System.out.println(createUserResponse.toString());

        // Deletando usuario
        String userId = createUserResponse.getUser().getId();
        DeleteUserResponse deleteUserResponse = userClient.deleteUser(
                DeleteUserRequest.newBuilder().setUserId(userId).build()
        );
        System.out.println(deleteUserResponse.toString());

        // Listando usuarios
        userClient.listUser(ListUserRequest.newBuilder().build()).forEachRemaining(
                listUserResponse -> System.out.println(listUserResponse.getUser().toString())
        );
    }
}

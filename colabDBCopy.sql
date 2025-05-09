PGDMP  !    /                }            colabDB    17.4    17.4 I    W           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            X           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            Y           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            Z           1262    41432    colabDB    DATABASE     o   CREATE DATABASE "colabDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en-US';
    DROP DATABASE "colabDB";
                     postgres    false            �            1259    41497 	   agreement    TABLE     �  CREATE TABLE public.agreement (
    agreement_id integer NOT NULL,
    user_id integer NOT NULL,
    service_id integer NOT NULL,
    property_id integer NOT NULL,
    status character varying(20),
    start_date date NOT NULL,
    end_date date NOT NULL,
    time_duration integer NOT NULL,
    cost numeric(10,2) NOT NULL,
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT agreement_status_check CHECK (((status)::text = ANY ((ARRAY['pending'::character varying, 'accepted'::character varying, 'rejected'::character varying, 'completed'::character varying])::text[]))),
    CONSTRAINT agreement_time_duration_check CHECK ((time_duration > 0))
);
    DROP TABLE public.agreement;
       public         heap r       postgres    false            �            1259    41496    agreement_agreement_id_seq    SEQUENCE     �   CREATE SEQUENCE public.agreement_agreement_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.agreement_agreement_id_seq;
       public               postgres    false    226            [           0    0    agreement_agreement_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.agreement_agreement_id_seq OWNED BY public.agreement.agreement_id;
          public               postgres    false    225            �            1259    41549    government_scheme_application    TABLE     �  CREATE TABLE public.government_scheme_application (
    application_id integer NOT NULL,
    scheme_id integer NOT NULL,
    farmer_id integer NOT NULL,
    register_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    status character varying(20),
    CONSTRAINT government_scheme_application_status_check CHECK (((status)::text = ANY ((ARRAY['Pending'::character varying, 'Accepted'::character varying, 'Rejected'::character varying])::text[])))
);
 1   DROP TABLE public.government_scheme_application;
       public         heap r       postgres    false            �            1259    41548 0   government_scheme_application_application_id_seq    SEQUENCE     �   CREATE SEQUENCE public.government_scheme_application_application_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 G   DROP SEQUENCE public.government_scheme_application_application_id_seq;
       public               postgres    false    232            \           0    0 0   government_scheme_application_application_id_seq    SEQUENCE OWNED BY     �   ALTER SEQUENCE public.government_scheme_application_application_id_seq OWNED BY public.government_scheme_application.application_id;
          public               postgres    false    231            �            1259    41539    government_schemes    TABLE     :  CREATE TABLE public.government_schemes (
    scheme_id integer NOT NULL,
    title character varying(255) NOT NULL,
    benefit_amount numeric(10,2) NOT NULL,
    start_date date NOT NULL,
    last_date date NOT NULL,
    description text,
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
 &   DROP TABLE public.government_schemes;
       public         heap r       postgres    false            �            1259    41538     government_schemes_scheme_id_seq    SEQUENCE     �   CREATE SEQUENCE public.government_schemes_scheme_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.government_schemes_scheme_id_seq;
       public               postgres    false    230            ]           0    0     government_schemes_scheme_id_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.government_schemes_scheme_id_seq OWNED BY public.government_schemes.scheme_id;
          public               postgres    false    229            �            1259    41522    payment    TABLE     �  CREATE TABLE public.payment (
    payment_id integer NOT NULL,
    agreement_id integer NOT NULL,
    total_amount numeric(10,2) NOT NULL,
    payment_method character varying(50),
    payment_mode character varying(50),
    payment_status character varying(50),
    transaction_id character varying(255),
    received_date date,
    payment_due_date date NOT NULL,
    CONSTRAINT payment_payment_method_check CHECK (((payment_method)::text = ANY ((ARRAY['UPI'::character varying, 'Credit Card'::character varying, 'Debit Card'::character varying, 'Net Banking'::character varying])::text[]))),
    CONSTRAINT payment_payment_mode_check CHECK (((payment_mode)::text = ANY ((ARRAY['Online'::character varying, 'Offline'::character varying])::text[]))),
    CONSTRAINT payment_payment_status_check CHECK (((payment_status)::text = ANY ((ARRAY['Paid'::character varying, 'Pending'::character varying, 'Failed'::character varying])::text[])))
);
    DROP TABLE public.payment;
       public         heap r       postgres    false            �            1259    41521    payment_payment_id_seq    SEQUENCE     �   CREATE SEQUENCE public.payment_payment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.payment_payment_id_seq;
       public               postgres    false    228            ^           0    0    payment_payment_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.payment_payment_id_seq OWNED BY public.payment.payment_id;
          public               postgres    false    227            �            1259    41449    property    TABLE     �  CREATE TABLE public.property (
    property_id integer NOT NULL,
    farmer_id integer NOT NULL,
    village character varying(255) NOT NULL,
    taluka character varying(255) NOT NULL,
    district character varying(255) NOT NULL,
    state character varying(255) NOT NULL,
    type_of_land character varying(50),
    land_image character varying(255),
    document_image character varying(255),
    area_acre numeric(10,2) NOT NULL,
    lease_price numeric(10,2) NOT NULL,
    area_guntha numeric(10,2),
    status character varying(20),
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT property_status_check CHECK (((status)::text = ANY ((ARRAY['Available'::character varying, 'Not Available'::character varying])::text[]))),
    CONSTRAINT property_type_of_land_check CHECK (((type_of_land)::text = ANY ((ARRAY['Irrigated Land'::character varying, 'Rainfed Land'::character varying])::text[])))
);
    DROP TABLE public.property;
       public         heap r       postgres    false            �            1259    41448    property_property_id_seq    SEQUENCE     �   CREATE SEQUENCE public.property_property_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.property_property_id_seq;
       public               postgres    false    220            _           0    0    property_property_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.property_property_id_seq OWNED BY public.property.property_id;
          public               postgres    false    219            �            1259    41475    service_provider    TABLE       CREATE TABLE public.service_provider (
    id integer NOT NULL,
    service_id integer NOT NULL,
    user_id integer NOT NULL,
    price numeric(10,2) NOT NULL,
    area numeric(10,2) NOT NULL,
    available boolean DEFAULT true NOT NULL,
    village character varying(255) NOT NULL,
    taluka character varying(255) NOT NULL,
    district character varying(255) NOT NULL,
    state character varying(255) NOT NULL,
    description text,
    service_type character varying(50),
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT service_provider_service_type_check CHECK (((service_type)::text = ANY ((ARRAY['Equipment'::character varying, 'Testing'::character varying, 'Labour'::character varying, 'Development'::character varying])::text[])))
);
 $   DROP TABLE public.service_provider;
       public         heap r       postgres    false            �            1259    41474    service_provider_id_seq    SEQUENCE     �   CREATE SEQUENCE public.service_provider_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.service_provider_id_seq;
       public               postgres    false    224            `           0    0    service_provider_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.service_provider_id_seq OWNED BY public.service_provider.id;
          public               postgres    false    223            �            1259    41466    services    TABLE     t   CREATE TABLE public.services (
    service_id integer NOT NULL,
    service_name character varying(255) NOT NULL
);
    DROP TABLE public.services;
       public         heap r       postgres    false            �            1259    41465    services_service_id_seq    SEQUENCE     �   CREATE SEQUENCE public.services_service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.services_service_id_seq;
       public               postgres    false    222            a           0    0    services_service_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.services_service_id_seq OWNED BY public.services.service_id;
          public               postgres    false    221            �            1259    41434    users    TABLE     j  CREATE TABLE public.users (
    user_id integer NOT NULL,
    name character varying(255) NOT NULL,
    contact character varying(15) NOT NULL,
    email character varying(255) NOT NULL,
    address text,
    password character varying(255) NOT NULL,
    type_of_user character varying(20),
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_type_of_user_check CHECK (((type_of_user)::text = ANY ((ARRAY['admin'::character varying, 'farmer'::character varying, 'company'::character varying, 'service_provider'::character varying, 'landowner'::character varying])::text[])))
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    41433    users_user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.users_user_id_seq;
       public               postgres    false    218            b           0    0    users_user_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;
          public               postgres    false    217            �           2604    41500    agreement agreement_id    DEFAULT     �   ALTER TABLE ONLY public.agreement ALTER COLUMN agreement_id SET DEFAULT nextval('public.agreement_agreement_id_seq'::regclass);
 E   ALTER TABLE public.agreement ALTER COLUMN agreement_id DROP DEFAULT;
       public               postgres    false    226    225    226            �           2604    41552 ,   government_scheme_application application_id    DEFAULT     �   ALTER TABLE ONLY public.government_scheme_application ALTER COLUMN application_id SET DEFAULT nextval('public.government_scheme_application_application_id_seq'::regclass);
 [   ALTER TABLE public.government_scheme_application ALTER COLUMN application_id DROP DEFAULT;
       public               postgres    false    232    231    232            �           2604    41542    government_schemes scheme_id    DEFAULT     �   ALTER TABLE ONLY public.government_schemes ALTER COLUMN scheme_id SET DEFAULT nextval('public.government_schemes_scheme_id_seq'::regclass);
 K   ALTER TABLE public.government_schemes ALTER COLUMN scheme_id DROP DEFAULT;
       public               postgres    false    230    229    230            �           2604    41525    payment payment_id    DEFAULT     x   ALTER TABLE ONLY public.payment ALTER COLUMN payment_id SET DEFAULT nextval('public.payment_payment_id_seq'::regclass);
 A   ALTER TABLE public.payment ALTER COLUMN payment_id DROP DEFAULT;
       public               postgres    false    228    227    228            |           2604    41452    property property_id    DEFAULT     |   ALTER TABLE ONLY public.property ALTER COLUMN property_id SET DEFAULT nextval('public.property_property_id_seq'::regclass);
 C   ALTER TABLE public.property ALTER COLUMN property_id DROP DEFAULT;
       public               postgres    false    220    219    220                       2604    41478    service_provider id    DEFAULT     z   ALTER TABLE ONLY public.service_provider ALTER COLUMN id SET DEFAULT nextval('public.service_provider_id_seq'::regclass);
 B   ALTER TABLE public.service_provider ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    224    223    224            ~           2604    41469    services service_id    DEFAULT     z   ALTER TABLE ONLY public.services ALTER COLUMN service_id SET DEFAULT nextval('public.services_service_id_seq'::regclass);
 B   ALTER TABLE public.services ALTER COLUMN service_id DROP DEFAULT;
       public               postgres    false    221    222    222            z           2604    41437    users user_id    DEFAULT     n   ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);
 <   ALTER TABLE public.users ALTER COLUMN user_id DROP DEFAULT;
       public               postgres    false    218    217    218            N          0    41497 	   agreement 
   TABLE DATA           �   COPY public.agreement (agreement_id, user_id, service_id, property_id, status, start_date, end_date, time_duration, cost, create_date) FROM stdin;
    public               postgres    false    226   Uk       T          0    41549    government_scheme_application 
   TABLE DATA           t   COPY public.government_scheme_application (application_id, scheme_id, farmer_id, register_date, status) FROM stdin;
    public               postgres    false    232   `l       R          0    41539    government_schemes 
   TABLE DATA           �   COPY public.government_schemes (scheme_id, title, benefit_amount, start_date, last_date, description, created_date) FROM stdin;
    public               postgres    false    230   �l       P          0    41522    payment 
   TABLE DATA           �   COPY public.payment (payment_id, agreement_id, total_amount, payment_method, payment_mode, payment_status, transaction_id, received_date, payment_due_date) FROM stdin;
    public               postgres    false    228   .o       H          0    41449    property 
   TABLE DATA           �   COPY public.property (property_id, farmer_id, village, taluka, district, state, type_of_land, land_image, document_image, area_acre, lease_price, area_guntha, status, create_date) FROM stdin;
    public               postgres    false    220   �o       L          0    41475    service_provider 
   TABLE DATA           �   COPY public.service_provider (id, service_id, user_id, price, area, available, village, taluka, district, state, description, service_type, create_date) FROM stdin;
    public               postgres    false    224   �q       J          0    41466    services 
   TABLE DATA           <   COPY public.services (service_id, service_name) FROM stdin;
    public               postgres    false    222   t       F          0    41434    users 
   TABLE DATA           l   COPY public.users (user_id, name, contact, email, address, password, type_of_user, create_date) FROM stdin;
    public               postgres    false    218   $u       c           0    0    agreement_agreement_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.agreement_agreement_id_seq', 11, true);
          public               postgres    false    225            d           0    0 0   government_scheme_application_application_id_seq    SEQUENCE SET     ^   SELECT pg_catalog.setval('public.government_scheme_application_application_id_seq', 6, true);
          public               postgres    false    231            e           0    0     government_schemes_scheme_id_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.government_schemes_scheme_id_seq', 10, true);
          public               postgres    false    229            f           0    0    payment_payment_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.payment_payment_id_seq', 5, true);
          public               postgres    false    227            g           0    0    property_property_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.property_property_id_seq', 14, true);
          public               postgres    false    219            h           0    0    service_provider_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.service_provider_id_seq', 15, true);
          public               postgres    false    223            i           0    0    services_service_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.services_service_id_seq', 15, true);
          public               postgres    false    221            j           0    0    users_user_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.users_user_id_seq', 15, true);
          public               postgres    false    217            �           2606    41505    agreement agreement_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.agreement
    ADD CONSTRAINT agreement_pkey PRIMARY KEY (agreement_id);
 B   ALTER TABLE ONLY public.agreement DROP CONSTRAINT agreement_pkey;
       public                 postgres    false    226            �           2606    41556 @   government_scheme_application government_scheme_application_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.government_scheme_application
    ADD CONSTRAINT government_scheme_application_pkey PRIMARY KEY (application_id);
 j   ALTER TABLE ONLY public.government_scheme_application DROP CONSTRAINT government_scheme_application_pkey;
       public                 postgres    false    232            �           2606    41547 *   government_schemes government_schemes_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.government_schemes
    ADD CONSTRAINT government_schemes_pkey PRIMARY KEY (scheme_id);
 T   ALTER TABLE ONLY public.government_schemes DROP CONSTRAINT government_schemes_pkey;
       public                 postgres    false    230            �           2606    41530    payment payment_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_pkey PRIMARY KEY (payment_id);
 >   ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_pkey;
       public                 postgres    false    228            �           2606    41532 "   payment payment_transaction_id_key 
   CONSTRAINT     g   ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_transaction_id_key UNIQUE (transaction_id);
 L   ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_transaction_id_key;
       public                 postgres    false    228            �           2606    41459    property property_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.property
    ADD CONSTRAINT property_pkey PRIMARY KEY (property_id);
 @   ALTER TABLE ONLY public.property DROP CONSTRAINT property_pkey;
       public                 postgres    false    220            �           2606    41485 &   service_provider service_provider_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.service_provider
    ADD CONSTRAINT service_provider_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.service_provider DROP CONSTRAINT service_provider_pkey;
       public                 postgres    false    224            �           2606    41471    services services_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (service_id);
 @   ALTER TABLE ONLY public.services DROP CONSTRAINT services_pkey;
       public                 postgres    false    222            �           2606    41473 "   services services_service_name_key 
   CONSTRAINT     e   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_service_name_key UNIQUE (service_name);
 L   ALTER TABLE ONLY public.services DROP CONSTRAINT services_service_name_key;
       public                 postgres    false    222            �           2606    41445    users users_contact_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_contact_key UNIQUE (contact);
 A   ALTER TABLE ONLY public.users DROP CONSTRAINT users_contact_key;
       public                 postgres    false    218            �           2606    41447    users users_email_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT users_email_key;
       public                 postgres    false    218            �           2606    41443    users users_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    218            �           2606    41516 $   agreement agreement_property_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.agreement
    ADD CONSTRAINT agreement_property_id_fkey FOREIGN KEY (property_id) REFERENCES public.property(property_id) ON DELETE CASCADE;
 N   ALTER TABLE ONLY public.agreement DROP CONSTRAINT agreement_property_id_fkey;
       public               postgres    false    226    220    4762            �           2606    41511 #   agreement agreement_service_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.agreement
    ADD CONSTRAINT agreement_service_id_fkey FOREIGN KEY (service_id) REFERENCES public.services(service_id) ON DELETE CASCADE;
 M   ALTER TABLE ONLY public.agreement DROP CONSTRAINT agreement_service_id_fkey;
       public               postgres    false    222    4764    226            �           2606    41506     agreement agreement_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.agreement
    ADD CONSTRAINT agreement_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;
 J   ALTER TABLE ONLY public.agreement DROP CONSTRAINT agreement_user_id_fkey;
       public               postgres    false    218    4760    226            �           2606    41562 J   government_scheme_application government_scheme_application_farmer_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.government_scheme_application
    ADD CONSTRAINT government_scheme_application_farmer_id_fkey FOREIGN KEY (farmer_id) REFERENCES public.users(user_id) ON DELETE CASCADE;
 t   ALTER TABLE ONLY public.government_scheme_application DROP CONSTRAINT government_scheme_application_farmer_id_fkey;
       public               postgres    false    218    232    4760            �           2606    41557 J   government_scheme_application government_scheme_application_scheme_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.government_scheme_application
    ADD CONSTRAINT government_scheme_application_scheme_id_fkey FOREIGN KEY (scheme_id) REFERENCES public.government_schemes(scheme_id) ON DELETE CASCADE;
 t   ALTER TABLE ONLY public.government_scheme_application DROP CONSTRAINT government_scheme_application_scheme_id_fkey;
       public               postgres    false    4776    230    232            �           2606    41533 !   payment payment_agreement_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_agreement_id_fkey FOREIGN KEY (agreement_id) REFERENCES public.agreement(agreement_id) ON DELETE CASCADE;
 K   ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_agreement_id_fkey;
       public               postgres    false    4770    226    228            �           2606    41460     property property_farmer_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.property
    ADD CONSTRAINT property_farmer_id_fkey FOREIGN KEY (farmer_id) REFERENCES public.users(user_id) ON DELETE CASCADE;
 J   ALTER TABLE ONLY public.property DROP CONSTRAINT property_farmer_id_fkey;
       public               postgres    false    220    4760    218            �           2606    41486 1   service_provider service_provider_service_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.service_provider
    ADD CONSTRAINT service_provider_service_id_fkey FOREIGN KEY (service_id) REFERENCES public.services(service_id) ON DELETE CASCADE;
 [   ALTER TABLE ONLY public.service_provider DROP CONSTRAINT service_provider_service_id_fkey;
       public               postgres    false    4764    224    222            �           2606    41491 .   service_provider service_provider_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.service_provider
    ADD CONSTRAINT service_provider_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;
 X   ALTER TABLE ONLY public.service_provider DROP CONSTRAINT service_provider_user_id_fkey;
       public               postgres    false    4760    218    224            N   �   x���KN�0E��*����&�/B xT���IJ[)(��N�O.������xF�ʍp�

���Gօ�J��='eΎA��m�n��1f�q�FX??����!�1,�,�s�u����s�����jom�+��+s�e�2��u�>A6X��ur?���B�+�i��W��J�2���H\����Qi�PC�L>��98��+q~E�z/X����א#�#2���v,��e�8W�>�P��s?@��      T   i   x�3�4�4�4202�50�50T00�#΀Լ�̼t.�,�1B�B�crrjAIj
�1P�	B�1BIPjVj2H�	P�)B�	�=�@y3�
S,���#��a���� ?�,�      R   E  x��TKO�0>{��CP��E<$��R�^g�8vj;T��;�.	[U[�(��x�׌3u��[ha�u-�;.+V�4MO�T�i>M�L��k�'E��آՌB�4�GxvB���3��|�~�%i��3ȋ�iz��O�X.�I�֎��X��ֺ��d�G�|�M�"U�޽qIB�=R�l7=��H�wl�Ly�PW��7�~���f���u�]]���"� 7(��|1P�!�G�T�l��Tr�U��`�{ؾ�����N�JaQ
ơ��O8<Sw�Y!w��K������Z �F�i���j'�6��m�������te�WKG9�%/,+��B=í�P1���
�D��g�.�G2�΅����,�&�=ozq�x�B:e�P*���;�G~�0���b�wNܐi�D/�{֭����K�ղ&(鍌kj�������|_�l·�$&�W�_Uq�g9�Y����G��o����5�O7ȩZa�Q��<�z��ucܶS�-J�G�k?��u:R\v�)�}%+�>��r�㭓ɬh��M��gp��$�|H��u&9�A�z��ƣ�����<�L& �Xp�      P   �   x�e���0�继� �kK�*��4qp���Fҁ��Q�X���/ߗ�*@ k��.�Nq�C�B�k#X*L�s��ijd`(iA��wa�j7v���؊'Ӵ(A��E��������]��9D0'�8M�
�5��S�s���֐���S�ҺLd��P����#����Jx�9
o"� ��L�      H   �  x����R�@�u�+��4/=�9	v �Qlڑ���R�|F�ꖈU����]���s%@�CU�X���1�w��a�k��{���m[��+���j��m	�����0��D�׿������U]���9�ԉ4�T�ʜ%2af#��;,�������iL�D����3&Ho�̈́�D���K�7Xb{,E� q?9&H���`��76�r۵���$�N�/���p����/���g��7]SK�8���jb�$bHX��S���DL��	���{�@ʄs"��զ^���1DB��1T?WQ��"�����E����p)	҉꿔��	�D��Ŧ��Ǣ��M܎�M�i)B��i��=|���I��/�����P�W��:�ZS�9t��+�%�	b����������'��!��ǐ���>�!�?������B۪c�r���[���У�x�E����p6��@i	R      L   +  x��T�n�0=�_����j�ܜnA�ň���H��i)RQrӯ�P�[�H1�ux%�'�fQ$�pxq��u��#X�	Z��y�@<z�uh�W�G[�VQ��j���rI�q�[�p�B$Q�M�t�d2I���z�΢e��'��8�v��X@#�<�b�kUZ��F6���J��S ҵᄲ[�R���ZY/>@�:�����8�F�@_j6`��������,���`���ڕ��D"����g�oO��G"r8rH6P�=����tV�����p'5RP�s<�G�_TΪ_��#�U��
K� |�'ÝC�&װf���C��G��K�-V���ze܋�q5�����-p�������h���[��>v`�?q9�[O�wC �b^ʃ{8$���q������p�F�?Ih��!���hH{As�1�����v�W���l9�q������u��5� Tζ�6p��n`4D1��G7��s[�*�X��1��ߡ=�1�Tti\�@ �F�*�2�3<S����ȷϰ��1n����:�L&� |�      J   �   x�e��N1���S�	�?G��R$*[�7�v-�I�f��ӓ��7���7ca��rT|�ɛtQ<�x�z3��:990vI�T�xR���Ā��Iұ��В��qR܈V��4&�� V7��1��1���e�e���R���s���8��[
�sE{/�S�Z|���P֞��*���;��DO��9lH�.���$��w]@�1��S��)86v	�Ly(Aۨ��"�f�qe��|Hv�      F   �  x����n�0 �����/	�Ԅ��MH�R@�*R�J������wNƚ�'cO\WY�;O����`b�&�bY�M��"��!O���a�m��9.�r���u_#� <�d@�n/���;&�ʒ�	�_��BC��=�o:*OHry����C�E��ۧ�MTdGM��<J)��n�d��5����G57���{�`��T�����塑�Q��Ɯ��.�556�{�o��v���	�S��PC�e�5+�&kbg�aXd%˿�X\�
X�����+KML'
M�/qA���S�/�8Y�r�2\:���� �"�K�vWl7���T[хYR}��ęV�G���tؕq����d��R5��sw.�����[��g�[���B\��~!/Z?f<��.�c������%u�Vo���J|��K��{uy�ʣ֡9�Z���N������     